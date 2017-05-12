package managedbean.consulta;

import java.util.Date;

public class VeiculoViagem {
    private String placa;
    private String motorista;
    private Long viagem;
    private String statusViagem;
    private Date dataPrevista;
    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    public String getMotorista() {
        return motorista;
    }
    public void setMotorista(String motorista) {
        this.motorista = motorista;
    }
    public Long getViagem() {
        return viagem;
    }
    public void setViagem(Long viagem) {
        this.viagem = viagem;
    }
    public String getStatusViagem() {
        return statusViagem;
    }
    public void setStatusViagem(String statusViagem) {
        this.statusViagem = statusViagem;
    }
    public Date getDataPrevista() {
        return dataPrevista;
    }
    public void setDataPrevista(Date dataPrevista) {
        this.dataPrevista = dataPrevista;
    }
    
    
}
