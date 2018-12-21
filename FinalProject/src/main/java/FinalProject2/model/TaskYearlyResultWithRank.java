package FinalProject2.model;

public class TaskYearlyResultWithRank {

	private TaskYearlyResult taskYearlyResult;
	private int pRankDepartment, pRankAll;
	private int aRankDepartment, aRankAll;
	
	public int getaRankDepartment() {
		return aRankDepartment;
	}
	public void setaRankDepartment(int aRankDepartment) {
		this.aRankDepartment = aRankDepartment;
	}
	private int paramDepartment, paramAll;
	
	public TaskYearlyResult getTaskYearlyResult() {
		return taskYearlyResult;
	}
	public void setTaskYearlyResult(TaskYearlyResult taskYearlyResult) {
		this.taskYearlyResult = taskYearlyResult;
	}
	public int getpRankDepartment() {
		return pRankDepartment;
	}
	public void setpRankDepartment(int pRankDepartment) {
		this.pRankDepartment = pRankDepartment;
	}
	public int getpRankAll() {
		return pRankAll;
	}
	public void setpRankAll(int pRankAll) {
		this.pRankAll = pRankAll;
	}
	public int getaRankAll() {
		return aRankAll;
	}
	public void setaRankAll(int aRankAll) {
		this.aRankAll = aRankAll;
	}
	public int getParamDepartment() {
		return paramDepartment;
	}
	public void setParamDepartment(int paramDepartment) {
		this.paramDepartment = paramDepartment;
	}
	public int getParamAll() {
		return paramAll;
	}
	public void setParamAll(int paramAll) {
		this.paramAll = paramAll;
	}

}
