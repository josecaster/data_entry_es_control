package software.simple.solutions.data.entry.es.control.liquibase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CustomChangeException;
import liquibase.exception.DatabaseException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;
import software.simple.solutions.framework.core.liquibase.CustomDataTaskChange;

public class DataOauthClientDetails extends CustomDataTaskChange {

	private JdbcConnection connection;
	private String clientId, clientSecret, scope, authorizedGrantTypes, authorities, accessTokenValidity,
			refreshTokenValidity;

	@Override
	public void execute(Database database) throws CustomChangeException {
		connection = (JdbcConnection) database.getConnection();

		try {
			String query = "select client_id from oauth_client_details where client_id=?";
			PreparedStatement prepareStatement = connection.prepareStatement(query);
			setData(prepareStatement, 1, clientId);
			ResultSet resultSet = prepareStatement.executeQuery();

			boolean exists = false;
			while (resultSet.next()) {
				exists = true;
			}
			resultSet.close();
			prepareStatement.close();

			if (exists) {
				String update = "update oauth_client_details set client_secret=?, scope=?, authorized_grant_types=?, authorities=?, access_token_validity=?, refresh_token_validity=?  where client_id=?";
				prepareStatement = connection.prepareStatement(update);
				setData(prepareStatement, 1, clientSecret);
				setData(prepareStatement, 2, scope);
				setData(prepareStatement, 3, authorizedGrantTypes);
				setData(prepareStatement, 4, authorities);
				setData(prepareStatement, 5, accessTokenValidity);
				setData(prepareStatement, 6, refreshTokenValidity);
				setData(prepareStatement, 7, clientId);
				prepareStatement.executeUpdate();
				prepareStatement.close();
			} else {
				String insert = "insert into oauth_client_details(client_secret,scope,authorized_grant_types,authorities,access_token_validity,refresh_token_validity,client_id) "
						+ "values(?,?,?,?,?,?,?)";
				prepareStatement = connection.prepareStatement(insert);
				setData(prepareStatement, 1, clientSecret);
				setData(prepareStatement, 2, scope);
				setData(prepareStatement, 3, authorizedGrantTypes);
				setData(prepareStatement, 4, authorities);
				setData(prepareStatement, 5, accessTokenValidity);
				setData(prepareStatement, 6, refreshTokenValidity);
				setData(prepareStatement, 7, clientId);
				prepareStatement.executeUpdate();
				prepareStatement.close();
			}
		} catch (SQLException | DatabaseException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String getConfirmationMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFileOpener(ResourceAccessor arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUp() throws SetupException {
		// TODO Auto-generated method stub

	}

	@Override
	public ValidationErrors validate(Database arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public JdbcConnection getConnection() {
		return connection;
	}

	public void setConnection(JdbcConnection connection) {
		this.connection = connection;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}

	public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
	}

	public String getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}

	public String getAccessTokenValidity() {
		return accessTokenValidity;
	}

	public void setAccessTokenValidity(String accessTokenValidity) {
		this.accessTokenValidity = accessTokenValidity;
	}

	public String getRefreshTokenValidity() {
		return refreshTokenValidity;
	}

	public void setRefreshTokenValidity(String refreshTokenValidity) {
		this.refreshTokenValidity = refreshTokenValidity;
	}

}
