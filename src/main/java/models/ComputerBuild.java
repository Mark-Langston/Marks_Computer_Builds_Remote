package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class ComputerBuild {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty title = new SimpleStringProperty();
    private StringProperty caseType = new SimpleStringProperty();
    private StringProperty motherboard = new SimpleStringProperty();
    private StringProperty cpu = new SimpleStringProperty();
    private StringProperty cpuCooler = new SimpleStringProperty();
    private StringProperty ram = new SimpleStringProperty();
    private StringProperty gpu = new SimpleStringProperty();
    private StringProperty powerSupply = new SimpleStringProperty();
    private StringProperty massStorage = new SimpleStringProperty();

    // Constructor with ID
    public ComputerBuild(int id, String title, String caseType, String motherboard, String cpu, String cpuCooler, String ram, String gpu, String powerSupply, String massStorage) {
        this.id.set(id);
        this.title.set(title);
        this.caseType.set(caseType);
        this.motherboard.set(motherboard);
        this.cpu.set(cpu);
        this.cpuCooler.set(cpuCooler);
        this.ram.set(ram);
        this.gpu.set(gpu);
        this.powerSupply.set(powerSupply);
        this.massStorage.set(massStorage);
    }

    // Constructor without ID (for adding new builds)
    public ComputerBuild(String title, String caseType, String motherboard, String cpu, String cpuCooler, String ram, String gpu, String powerSupply, String massStorage) {
        this(-1, title, caseType, motherboard, cpu, cpuCooler, ram, gpu, powerSupply, massStorage);
    }

    // Getters and Setters
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getCaseType() {
        return caseType.get();
    }

    public void setCaseType(String caseType) {
        this.caseType.set(caseType);
    }

    public StringProperty caseTypeProperty() {
        return caseType;
    }

    public String getMotherboard() {
        return motherboard.get();
    }

    public void setMotherboard(String motherboard) {
        this.motherboard.set(motherboard);
    }

    public StringProperty motherboardProperty() {
        return motherboard;
    }

    public String getCpu() {
        return cpu.get();
    }

    public void setCpu(String cpu) {
        this.cpu.set(cpu);
    }

    public StringProperty cpuProperty() {
        return cpu;
    }

    public String getCpuCooler() {
        return cpuCooler.get();
    }

    public void setCpuCooler(String cpuCooler) {
        this.cpuCooler.set(cpuCooler);
    }

    public StringProperty cpuCoolerProperty() {
        return cpuCooler;
    }

    public String getRam() {
        return ram.get();
    }

    public void setRam(String ram) {
        this.ram.set(ram);
    }

    public StringProperty ramProperty() {
        return ram;
    }

    public String getGpu() {
        return gpu.get();
    }

    public void setGpu(String gpu) {
        this.gpu.set(gpu);
    }

    public StringProperty gpuProperty() {
        return gpu;
    }

    public String getPowerSupply() {
        return powerSupply.get();
    }

    public void setPowerSupply(String powerSupply) {
        this.powerSupply.set(powerSupply);
    }

    public StringProperty powerSupplyProperty() {
        return powerSupply;
    }

    public String getMassStorage() {
        return massStorage.get();
    }

    public void setMassStorage(String massStorage) {
        this.massStorage.set(massStorage);
    }

    public StringProperty massStorageProperty() {
        return massStorage;
    }
}