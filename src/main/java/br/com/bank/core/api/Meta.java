package br.com.bank.core.api;

public class Meta {

    private String processName;
    private String status;

    public Meta() {}

    public Meta(String processName, String status) {
        this.processName = processName;
        this.status = status;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
