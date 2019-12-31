package cn.tcc.foundation.es.cluster;

import java.util.List;

public class EsCluster {

    private String esClusterName;

    private boolean transportSniff;

    private List<String> address;


    public String getEsClusterName() {
        return esClusterName;
    }

    public void setEsClusterName(String esClusterName) {
        this.esClusterName = esClusterName;
    }

    public boolean isTransportSniff() {
        return transportSniff;
    }

    public void setTransportSniff(boolean transportSniff) {
        this.transportSniff = transportSniff;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }
}
