package com.soprasteria.mycompany.entity;

/**
 * 
 * @author ggranata
 *
 */
public class RecordFilter {
	private String columnName;
	private Operator operator;
	private String value;
	private JoinCondition joinCondition;

	public RecordFilter(String columnName, Operator operator, String value, JoinCondition joinCondition) {
		this.columnName = columnName;
		this.operator = operator;
		this.value = value;
		this.joinCondition = joinCondition;
	}

	public RecordFilter() {
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public JoinCondition getJoinCondition() {
		return joinCondition;
	}

	public void setJoinCondition(JoinCondition joinCondition) {
		this.joinCondition = joinCondition;
	}

	public enum Operator {
		equals("="), notEquals("!="), isNull("null"), notNull("!="), greaterEqual(">="), lessEqual("<="), greter(
				">"), less("<"), strictlyEquals("==="), strictlyNotEquals("!=="), and("&&"), or("||");

		private String condition;

		private Operator(String condition) {

			this.condition = condition;
		}

		public String getCondition() {
			return condition;
		}
	}

	// Join Condition for query
	public enum JoinCondition {

		and("and"), or("or"), like("LIKE"), notLike("NOT LIKE"), between("BETWEEN"), notBetween("NOT BETWEEN"), isNull(
				"NULL"), isNotNull("IS NOT NULL"), in("IN"), notIn("NOT IN");

		private String condition;

		private JoinCondition(String condition) {

			this.condition = condition;
		}

		public String getCondition() {
			return condition;
		}
	}
}
