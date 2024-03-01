package data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ConnectionTableData {
    private StringProperty client_id;
    private StringProperty ip;
    private StringProperty host;
    private StringProperty account_id;
    private StringProperty  lastRequest;
    private StringProperty  status;

    public ConnectionTableData() {
        this.client_id = new SimpleStringProperty();
        this.ip = new SimpleStringProperty();
        this.host = new SimpleStringProperty();
        this.account_id = new SimpleStringProperty();
        this.lastRequest = new SimpleStringProperty();
        this.status = new SimpleStringProperty();
    }

    public String getClient_id() {
        return client_id.get();
    }

    public StringProperty client_idProperty() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id.set(client_id);
    }

    public String getIp() {
        return ip.get();
    }

    public StringProperty ipProperty() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip.set(ip);
    }

    public String getHost() {
        return host.get();
    }

    public StringProperty hostProperty() {
        return host;
    }

    public void setHost(String host) {
        this.host.set(host);
    }

    public String getAccount_id() {
        return account_id.get();
    }

    public StringProperty account_idProperty() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id.set(account_id);
    }

    public String getLastRequest() {
        return lastRequest.get();
    }

    public StringProperty lastRequestProperty() {
        return lastRequest;
    }

    public void setLastRequest(String lastRequest) {
        this.lastRequest.set(lastRequest);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String toString() {
        return client_id.get() + ip.get() + host.get() + lastRequest.get() + status.get();
    }
}
