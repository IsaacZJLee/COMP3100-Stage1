public class dsJob { 
    Integer id;
    String type;
    Integer core;
    Integer submitTime;
    Integer estRuntime;
    Integer memory;
    Integer disk;

    public dsJob(String[] fieldServerMsg) { // Assign job attribute names with job element in config file
        type = fieldServerMsg[0];
        submitTime = Integer.parseInt(fieldServerMsg[1]);
        id = Integer.parseInt(fieldServerMsg[2]);
        estRuntime = Integer.parseInt(fieldServerMsg[3]);
        core = Integer.parseInt(fieldServerMsg[4]);
        memory = Integer.parseInt(fieldServerMsg[5]);
        disk = Integer.parseInt(fieldServerMsg[6].trim()); 
    }

    public void printJobDetails() {
        System.out.printf("-----%nJob Type: %s%nSubmit Time: %s%nJobID: %s%nRuntime: %s%nCore: %s%nMemory: %s%nDisk: %s%n", 
            this.type, this.submitTime, this.id, this.estRuntime, this.core,this.memory,this.disk);
    }
}
