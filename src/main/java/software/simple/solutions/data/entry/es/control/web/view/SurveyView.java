package software.simple.solutions.data.entry.es.control.web.view;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.MultiFileUpload;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadFinishedHandler;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadStateWindow;
import com.whitestein.vaadin.widgets.wtpdfviewer.WTPdfViewer;

import io.reactivex.subjects.BehaviorSubject;
import software.simple.solutions.data.entry.es.control.constants.EsControlTables;
import software.simple.solutions.data.entry.es.control.constants.EsReferenceKey;
import software.simple.solutions.data.entry.es.control.constants.TypeOfFile;
import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.data.entry.es.control.entities.SurveyGroup;
import software.simple.solutions.data.entry.es.control.entities.SurveySection;
import software.simple.solutions.data.entry.es.control.properties.EsControlConfigurationProperty;
import software.simple.solutions.data.entry.es.control.properties.SurveyProperty;
import software.simple.solutions.data.entry.es.control.service.ISurveyService;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyVO;
import software.simple.solutions.framework.core.annotations.SupportedPrivileges;
import software.simple.solutions.framework.core.components.CButton;
import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CTextArea;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.ConfirmWindow;
import software.simple.solutions.framework.core.components.ConfirmWindow.ConfirmationHandler;
import software.simple.solutions.framework.core.components.FilterView;
import software.simple.solutions.framework.core.components.FormView;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.constants.MimeType;
import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.entities.EntityFile;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.icons.CxodeIcons;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.IConfigurationService;
import software.simple.solutions.framework.core.service.IFileService;
import software.simple.solutions.framework.core.util.ComponentUtil;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.valueobjects.EntityFileVO;
import software.simple.solutions.framework.core.web.BasicTemplate;

@SupportedPrivileges
public class SurveyView extends BasicTemplate<Survey> {

	private static final Logger logger = LogManager.getLogger(SurveyView.class);

	private static final long serialVersionUID = 6503015064562511801L;
	private final BehaviorSubject<SurveySection> surveySectionObserver;
	private final BehaviorSubject<SurveyGroup> surveyGroupObserver;

	public SurveyView() {
		setEntityClass(Survey.class);
		setServiceClass(ISurveyService.class);
		setFilterClass(Filter.class);
		setFormClass(Form.class);

		surveySectionObserver = BehaviorSubject.create();
		addReferenceKey(EsReferenceKey.SURVEY_SECTION_OBSERVER, surveySectionObserver);

		surveyGroupObserver = BehaviorSubject.create();
		addReferenceKey(EsReferenceKey.SURVEY_GROUP_OBSERVER, surveyGroupObserver);
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(Survey::getName, SurveyProperty.NAME);
		addContainerProperty(Survey::getDescription, SurveyProperty.DESCRIPTION);
	}

	/**
	 * Filter implementation
	 * 
	 * @author yusuf
	 *
	 */
	public class Filter extends FilterView {

		private static final long serialVersionUID = 117780868467918033L;

		private CStringIntervalLayout nameFld;
		private CStringIntervalLayout descriptionFld;

		@Override
		public void executeBuild() {

			nameFld = addField(CStringIntervalLayout.class, SurveyProperty.NAME, 0, 0);

			descriptionFld = addField(CStringIntervalLayout.class, SurveyProperty.DESCRIPTION, 0, 1);
		}

		@Override
		public Object getCriteria() {
			SurveyVO vo = new SurveyVO();
			vo.setNameInterval(nameFld.getValue());
			vo.setDescriptionInterval(descriptionFld.getValue());
			return vo;
		}
	}

	public class Form extends FormView {

		private HorizontalLayout h1;
		private CGridLayout formGrid;
		private CTextField nameFld;
		private CTextArea descriptionFld;
		private CCheckBox activeFld;
		private VerticalLayout uploadFileLayout;
		private CButton toExternalWindowBtn;
		private HorizontalLayout fileActionLayout;

		private Survey survey;
		private EntityFile entityFile;

		@Override
		public void executeBuild() {
			h1 = new HorizontalLayout();
			h1.setMargin(false);
			addComponent(h1);

			formGrid = ComponentUtil.createGrid();
			h1.addComponent(formGrid);

			nameFld = formGrid.addField(CTextField.class, SurveyProperty.NAME, 0, 0);

			descriptionFld = formGrid.addField(CTextArea.class, SurveyProperty.DESCRIPTION, 0, 1);

			activeFld = formGrid.addField(CCheckBox.class, SystemProperty.SYSTEM_ENTITY_ACTIVE, 1, 0);

			uploadFileLayout = createUploadFileLayout();
			h1.addComponent(uploadFileLayout);
			// formGrid.addComponent(uploadFileLayout, 5, 0, 5, 3);
		}

		@Override
		public void handleNewForm() throws FrameworkException {
			activeFld.setValue(true);
			uploadFileLayout.setVisible(false);
		}

		@SuppressWarnings("unchecked")
		@Override
		public Survey setFormValues(Object entity) throws FrameworkException {
			survey = (Survey) entity;
			nameFld.setValue(survey.getName());
			descriptionFld.setValue(survey.getDescription());
			activeFld.setValue(survey.getActive());
			uploadFileLayout.setVisible(false);

			IConfigurationService configurationService = ContextProvider.getBean(IConfigurationService.class);
			Configuration configuration = configurationService
					.getByCode(EsControlConfigurationProperty.SURVEY_FILE_STORAGE_LOCATION);
			if (configuration != null && configuration.getValue() != null) {
				uploadFileLayout.setVisible(true);

				IFileService fileService = ContextProvider.getBean(IFileService.class);
				entityFile = fileService.findFileByEntityAndType(survey.getId().toString(),
						EsControlTables.SURVEYS_.NAME, TypeOfFile.DATA_ENTRY_FORM);

				if (entityFile != null && entityFile.getFilePath() != null) {
					try {
						byte[] fileContent = Files.readAllBytes(new File(entityFile.getFilePath()).toPath());
						setPdfViewerContent(fileContent, entityFile.getName());
					} catch (IOException e) {
						new MessageWindowHandler(e);
					}
				}
			}

			return survey;
		}

		private void setPdfViewerContent(byte[] objectArray, String fileName) {
			WTPdfViewer pdfViewer = new WTPdfViewer();
			setSeconContent(pdfViewer);
			// show file in pdf viewer
			StreamResource streamResource = new StreamResource(new StreamSource() {

				@Override
				public InputStream getStream() {
					InputStream targetStream = new ByteArrayInputStream(objectArray);
					return targetStream;
				}
			}, fileName == null ? "unknown" : fileName);
			pdfViewer.setResource(streamResource);
			BrowserWindowOpener browserWindowOpener = new BrowserWindowOpener(streamResource);
			browserWindowOpener.setFeatures("resizable");
			browserWindowOpener.extend(toExternalWindowBtn);
			fileActionLayout.setVisible(true);
			uploadFileLayout.setMargin(true);
			uploadFileLayout.addStyleName(ValoTheme.LAYOUT_CARD);
		}

		@Override
		public Object getFormValues() throws FrameworkException {
			SurveyVO vo = new SurveyVO();

			vo.setId(survey == null ? null : survey.getId());
			vo.setName(nameFld.getValue());
			vo.setDescription(descriptionFld.getValue());
			vo.setActive(activeFld.getValue());
			return vo;
		}

		private VerticalLayout createUploadFileLayout() {
			uploadFileLayout = new VerticalLayout();
			uploadFileLayout.setMargin(false);
			// uploadFileLayout.addStyleName(ValoTheme.LAYOUT_CARD);

			UploadStateWindow uploadStateWindow = new UploadStateWindow();
			UploadFinishedHandler uploadFinishedHandler = new UploadFinishedHandler() {

				private static final long serialVersionUID = 5790832474445256513L;

				@Override
				public void handleFile(InputStream stream, String fileName, String mimeType, long length,
						int filesLeftInQueue) {
					if (MimeType.MIME_APPLICATION_PDF.equalsIgnoreCase(mimeType)) {
						uploadFileToRepository(stream, fileName);
					} else {
						new MessageWindowHandler(SystemMessageProperty.INVALID_FILE_TYPE,
								new Arg().norm(MimeType.MIME_APPLICATION_PDF));
					}
				}
			};

			MultiFileUpload multiFileUpload = new MultiFileUpload(uploadFinishedHandler, uploadStateWindow);
			multiFileUpload.setMaxFileCount(1);
			multiFileUpload.setPanelCaption(PropertyResolver.getPropertyValueByLocale(SurveyProperty.UPLOAD_FILE_HERE));
			multiFileUpload.getSmartUpload().addStyleName(Style.UPLOAD_BUTTON);
			multiFileUpload.getSmartUpload().setUploadButtonIcon(VaadinIcons.UPLOAD);
			multiFileUpload.getSmartUpload().setUploadButtonCaptions(
					PropertyResolver.getPropertyValueByLocale(SurveyProperty.UPLOAD_FILE_HERE),
					PropertyResolver.getPropertyValueByLocale(SurveyProperty.UPLOAD_FILE_HERE));
			uploadFileLayout.addComponent(multiFileUpload);

			fileActionLayout = new HorizontalLayout();
			fileActionLayout.setWidth("100%");
			fileActionLayout.setVisible(false);
			fileActionLayout.addStyleName(ValoTheme.LAYOUT_WELL);
			uploadFileLayout.addComponent(fileActionLayout);

			CButton resetViewerBtn = new CButton();
			resetViewerBtn.setIcon(CxodeIcons.RESIZE);
			resetViewerBtn.addStyleName(Style.RESIZED_ICON_80);
			resetViewerBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
			fileActionLayout.addComponent(resetViewerBtn);
			resetViewerBtn.addClickListener(new ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					resetSplitPosition();
				}
			});

			toExternalWindowBtn = new CButton();
			toExternalWindowBtn.setIcon(CxodeIcons.OPEN_EXTERNAL);
			toExternalWindowBtn.addStyleName(Style.RESIZED_ICON_80);
			toExternalWindowBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
			fileActionLayout.addComponent(toExternalWindowBtn);
			toExternalWindowBtn.addClickListener(new ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					setSplitPosition(100);
				}
			});

			CButton deleteSurveyFileBtn = new CButton();
			deleteSurveyFileBtn.setIcon(CxodeIcons.DELETE);
			deleteSurveyFileBtn.addStyleName(Style.RESIZED_ICON_80);
			deleteSurveyFileBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
			fileActionLayout.addComponent(deleteSurveyFileBtn);
			deleteSurveyFileBtn.addClickListener(new ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					ConfirmWindow confirmWindow = new ConfirmWindow(SystemProperty.DELETE_HEADER,
							SystemProperty.DELETE_CONFIRMATION_REQUEST, SystemProperty.CONFIRM, SystemProperty.CANCEL);
					confirmWindow.execute(new ConfirmationHandler() {

						@Override
						public void handlePositive() {
							try {
								IFileService fileService = ContextProvider.getBean(IFileService.class);
								fileService.delete(EntityFile.class, entityFile.getId(),
										sessionHolder.getApplicationUser().getId());
								fileActionLayout.setVisible(false);
								uploadFileLayout.setMargin(false);
								uploadFileLayout.removeStyleName(ValoTheme.LAYOUT_CARD);
								hideSecondComponent();
							} catch (FrameworkException e) {
								new MessageWindowHandler(e);
							}
						}

						@Override
						public void handleNegative() {
							// TODO Auto-generated method stub

						}
					});

				}
			});

			return uploadFileLayout;
		}

		protected void uploadFileToRepository(InputStream stream, String fileName) {
			ISurveyService surveyService = ContextProvider.getBean(ISurveyService.class);

			EntityFileVO vo = new EntityFileVO();
			vo.setEntityId(survey.getId().toString());
			vo.setEntityName(EsControlTables.SURVEYS_.NAME);
			try {
				byte[] bytes = IOUtils.toByteArray(stream);
				vo.setFileObject(bytes);
				vo.setFilename(fileName);
				vo.setTypeOfFile(TypeOfFile.DATA_ENTRY_FORM);
				entityFile = surveyService.upLoadFile(vo);
				setPdfViewerContent(bytes, fileName);
			} catch (InvalidDataAccessResourceUsageException | IOException | FrameworkException e) {
				logger.error(e.getMessage(), e);
				new MessageWindowHandler(e);
			}
		}
	}

}
