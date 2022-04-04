#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.hibernate.generators;

/**
 *
 * @author ${author}
 *
 */
public class UserIdGenerator extends AbstractDBDataGenerator {

	/***/
	public UserIdGenerator() {
		super(UserIdGenerator.class);
	}

	@Override
	public final String newRandomKey() {
		return RandomGenerator.rand4Number() + "-" + RandomGenerator.rand4Number();
	}

	@Override
	public final String getTableName() {
		return "dp_user";
	}

	@Override
	public final String getColumnName() {
		return "_id";
	}

}
