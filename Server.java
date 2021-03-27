public class Server {

	public int sID;
	public String sType;
	public int sLimit;
	public int sRuntime;
	public float sRate;
	public int sCoreCount;
	public int sMemory;
	public int sDisk;
	public int sState;
	public int sAvailableTime;

	Server(int sID, String sType, int sLimit, int sRuntime, float sRate, int sCoreCount, int sMemory, int sDisk) {
		this.sID = sID;
		this.sType = sType;
		this.sLimit = sLimit;
		this.sRuntime = sRuntime;
		this.sRate = sRate;
		this.sCoreCount = sCoreCount;
		this.sMemory = sMemory;
		this.sDisk = sDisk;
	}

	Server(String sType, int sID, int sState, int sAvailableTime, int sCoreCount, int sMemory, int sDisk) {
		this.sType = sType;
		this.sID = sID;
		this.sState = sState;
		this.sAvailableTime = sAvailableTime;
		this.sCoreCount = sCoreCount;
		this.sMemory = sMemory;
		this.sDisk = sDisk;
	}
}