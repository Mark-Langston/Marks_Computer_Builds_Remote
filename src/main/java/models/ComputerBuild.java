package models;

public class ComputerBuild {
    private int id;
    private String title;
    private String caseType;
    private String motherboard;
    private String cpu;
    private String cpuCooler;
    private String ram;
    private String gpu;
    private String powerSupply;
    private String massStorage;

    // No-argument constructor
    public ComputerBuild() {
    }

    // Parameterized constructor
    public ComputerBuild(int id, String title, String caseType, String motherboard, String cpu, String cpuCooler, String ram, String gpu, String powerSupply, String massStorage) {
        this.id = id;
        this.title = title;
        this.caseType = caseType;
        this.motherboard = motherboard;
        this.cpu = cpu;
        this.cpuCooler = cpuCooler;
        this.ram = ram;
        this.gpu = gpu;
        this.powerSupply = powerSupply;
        this.massStorage = massStorage;
    }

    public ComputerBuild(String title, String caseType, String motherboard, String cpu, String cpuCooler, String ram, String gpu, String powerSupply, String massStorage) {
        this.title = title;
        this.caseType = caseType;
        this.motherboard = motherboard;
        this.cpu = cpu;
        this.cpuCooler = cpuCooler;
        this.ram = ram;
        this.gpu = gpu;
        this.powerSupply = powerSupply;
        this.massStorage = massStorage;
    }

    // Getter and setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getMotherboard() {
        return motherboard;
    }

    public void setMotherboard(String motherboard) {
        this.motherboard = motherboard;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getCpuCooler() {
        return cpuCooler;
    }

    public void setCpuCooler(String cpuCooler) {
        this.cpuCooler = cpuCooler;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public String getPowerSupply() {
        return powerSupply;
    }

    public void setPowerSupply(String powerSupply) {
        this.powerSupply = powerSupply;
    }

    public String getMassStorage() {
        return massStorage;
    }

    public void setMassStorage(String massStorage) {
        this.massStorage = massStorage;
    }
}
