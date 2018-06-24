package exemplo.listacontato.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Contato implements Parcelable {
    private Long id = 0L;
    private String nome = "";
    private String endereco = "";
    private String telefone = "";
    private String referencia = "";
    private String celular = "";
    private String foto = "";
    private String email = "";

    public Contato() {
    }

    public Contato(Parcel in) {
        String [] data = new String[8];
        in.readStringArray(data);
        setId(Long.valueOf(data[0]));
        setNome(data[1]);
        setEndereco(data[2]);
        setTelefone(data[3]);
        setReferencia(data[4]);
        setCelular(data[5]);
        setFoto(data[6]);
        setEmail(data[7]);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{ getId().toString(),
                getNome(), getEndereco(), getTelefone(),
                getReferencia(), getCelular(),getFoto(),
                getEmail()
        });
    }

    public static final Parcelable.Creator<Contato> CREATOR = new Parcelable.Creator<Contato>(){

        @Override
        public Contato createFromParcel(Parcel source) {
            return new Contato(source);
        }

        @Override
        public Contato[] newArray(int size) {
            return new Contato[size];
        }
    };
}
