package exemplo.listacontato.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import exemplo.listacontato.R;
import exemplo.listacontato.model.Contato;

public class ContatoEditActivity extends AppCompatActivity {

    private ImageButton foto;
    private View layout;
    private TextView nome;
    private TextView endereco;
    private TextView telefone;
    private TextView celular;
    private TextView email;
    private TextView referencia;
    private Contato contato;

    public final int CAMERA = 1;
    public final int GALERIA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato_edit);
        contato = getIntent().getParcelableExtra(getString(R.string.contato));
        foto = findViewById(R.id.fotoContato);
        nome = findViewById(R.id.nomeContato);
        endereco = findViewById(R.id.enderecoContato);
        telefone = findViewById(R.id.telefoneContato);
        celular = findViewById(R.id.celularContato);
        email = findViewById(R.id.emailContato);
        referencia = findViewById(R.id.referenciaContato);
        layout = findViewById(R.id.activity_contato_edit_layout);

        Button buttonSalvar = findViewById(R.id.buttonSalvarContato);
        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contato.setNome(nome.getText().toString());
                contato.setEndereco(endereco.getText().toString());
                contato.setTelefone(telefone.getText().toString());
                contato.setCelular(celular.getText().toString());
                contato.setEmail(email.getText().toString());
                contato.setReferencia(referencia.getText().toString());
                if(contato.getNome() == null || contato.getNome().isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.validacao_nome, Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra(getString(R.string.contato), contato);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertaImagem();
            }
        });

        nome.setText(contato.getNome());
        endereco.setText(contato.getEndereco());
        telefone.setText(contato.getTelefone());
        celular.setText(contato.getCelular());
        email.setText(contato.getEmail());
        referencia.setText(contato.getReferencia());
    }

    private void alertaImagem(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alertImagem);
        builder.setPositiveButton(R.string.camera, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tirarFoto();
            }
        });

        builder.setNegativeButton(R.string.galeria, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                carregarGaleria();
            }
        });
        builder.create().show();
    }

    private void tirarFoto(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            requestCameraPermission();
        }else{
            showCamera();
        }
    }

    private void showCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    private void requestCameraPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
            Snackbar.make(layout,R.string.camera_permission, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(ContatoEditActivity.this, new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA);
                }
            }).show();
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA);
        }
    }

    private void carregarGaleria(){

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CAMERA :
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    tirarFoto();
                }
                break;
            case GALERIA :
                
                break;
        }

    }
}
