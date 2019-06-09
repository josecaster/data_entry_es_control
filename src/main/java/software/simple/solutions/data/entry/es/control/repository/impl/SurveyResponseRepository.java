package software.simple.solutions.data.entry.es.control.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.data.entry.es.control.repository.ISurveyApplicationUserRepository;
import software.simple.solutions.data.entry.es.control.repository.ISurveyResponseRepository;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyResponseVO;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.JoinLeftBuilder;
import software.simple.solutions.framework.core.repository.impl.GenericRepository;

@Repository
public class SurveyResponseRepository extends GenericRepository implements ISurveyResponseRepository {

	@Autowired
	private ISurveyApplicationUserRepository surveyApplicationUserRepository;

	@Override
	public JoinLeftBuilder createJoinBuilder(Object o, CriteriaBuilder criteriaBuilder) {
		return new JoinLeftBuilder() {

			@Override
			public CriteriaBuilder build(Root<?> root, CriteriaBuilder criteriaBuilder) throws FrameworkException {
				SurveyResponseVO vo = (SurveyResponseVO) o;
				List<ApplicationUser> applicationUsers = surveyApplicationUserRepository
						.findApplicationUserBySurvey(vo.getSurveyId());
				List<Long> applicationUserIds = null;
				if (applicationUsers != null) {
					applicationUserIds = applicationUsers.stream().map(p -> p.getId()).collect(Collectors.toList());
				}
				if (applicationUserIds == null) {
					applicationUserIds = new ArrayList<Long>();
				}
				Join<SurveyResponse, ApplicationUser> fileLogAdministrationJoin = root
						.join(SurveyResponse.VAR_APPLICATION_USER, JoinType.INNER);
				fileLogAdministrationJoin
						.on(fileLogAdministrationJoin.get(SurveyResponse.VAR_ID).in(applicationUserIds));
				return criteriaBuilder;
			}
		};
	}

	@Override
	public SurveyResponse getByUniqueId(String uniqueId) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "from SurveyResponse where uniqueId=:uniqueId";
		paramMap.put("uniqueId", uniqueId);
		return getByQuery(query, paramMap);
	}

	@Override
	public List<SurveyResponse> findAllSurveyResponsesByUser(String username) throws FrameworkException {
		String query = "select sr from SurveyResponse sr " + "left join Survey surv on surv.id=sr.survey.id "
				+ "left join SurveyApplicationUser sau on sau.applicationUser.id = sr.applicationUser.id and sau.survey.id=surv.id "
				+ "where lower(sr.applicationUser.username)=lower(:username)";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("username", username);
		return createListQuery(query, paramMap);
	}

	@Override
	public List<String> findAllActiveSurveyResponseUuIdsByUser(String username) throws FrameworkException {
		String query = "select sr.uniqueId from SurveyResponse sr " + "left join Survey surv on surv.id=sr.survey.id "
				+ "left join SurveyApplicationUser sau on sau.applicationUser.id = sr.applicationUser.id and sau.survey.id=surv.id "
				+ "where lower(sr.applicationUser.username)=lower(:username) " + "and sr.active=:active";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("username", username);
		paramMap.put("active", true);
		return createListQuery(query, paramMap);
	}

}
