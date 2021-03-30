public class dsServer {
	//Assigning variables
	public int sID;
	public String type;
	public int limit;
	public int bootupTime;
	public float hourlyRate;
	public int coreCount;
	public int memory;
	public int disk;
	public int sState;
	public int sAvailableTime;

	dsServer(int sID, String type, int limit, int bootupTime, float hourlyRate, int coreCount, int memory, int disk) {
		//Assigning variables
		this.sID = sID;
		this.type = type;
		this.limit = limit;
		this.bootupTime = bootupTime;
		this.hourlyRate = hourlyRate;
		this.coreCount = coreCount;
		this.memory = memory;
		this.disk = disk;
	}

	dsServer(String type, int sID, int sState, int sAvailableTime, int coreCount, int memory, int disk) {
		//Assigning variables
		this.type = type;
		this.sID = sID;
		this.sState = sState;
		this.sAvailableTime = sAvailableTime;
		this.coreCount = coreCount;
		this.memory = memory;
		this.disk = disk;
	}
}