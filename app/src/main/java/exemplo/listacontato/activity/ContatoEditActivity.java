package exemplo.listacontato.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import exemplo.listacontato.R;
import exemplo.listacontato.model.Contato;

public class ContatoEditActivity extends AppCompatActivity {

    private View layout;
    private TextView nome;
    private TextView endereco;
    private TextView telefone;
    private TextView celular;
    private TextView email;
    private TextView referencia;
    private Contato contato;
    private ImageButton foto;

    public final int CAMERA = 1;
    public final int GALERIA = 2;

    public final String IMAGEM_DIR = "/FotosContatos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato_edit);
        contato = getIntent().getParcelableExtra(getString(R.string.contato));
        nome = findViewById(R.id.nomeContato);
        endereco = findViewById(R.id.enderecoContato);
        telefone = findViewById(R.id.telefoneContato);
        celular = findViewById(R.id.celularContato);
        email = findViewById(R.id.emailContato);
        referencia = findViewById(R.id.referenciaContato);
        layout = findViewById(R.id.activity_contato_edit_layout);
        foto = findViewById(R.id.fotoContato);

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
        File file = new File(contato.getFoto());
        if (file.exists()){
             Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
             foto.setImageBitmap(bitmap);
        }
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
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestGaleriaPermission();
        }else{
            showGaleria();
        }
    }

    private void showGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALERIA);
    }

    private void requestGaleriaPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Snackbar.make(layout,R.string.write_external_extorage_permission, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(ContatoEditActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, GALERIA);
                        }
                    }).show();
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, GALERIA);
        }
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
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    carregarGaleria();
                }
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RESULT_CANCELED || data == null){
            return;
        }else if(requestCode == GALERIA){
            try {
                Uri contentURI = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                contato.setFoto(saveImagem(bitmap));
                foto.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(requestCode == CAMERA){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            contato.setFoto(saveImagem(bitmap));
            foto.setImageBitmap(bitmap);
        }
    }

    private String saveImagem(Bitmap bitmap){
        ByteArrayOutputStream bystes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bystes);
        File directory = new File(Environment.getExternalStorageDirectory()+ IMAGEM_DIR);
        if(!directory.exists()){
            directory.mkdirs();
        }

        try {
            File f = new File(directory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bystes.toByteArray());
            MediaScannerConnection.scanFile(this, new String[]{f.getPath()}, new String[]{"image/jpeg"}, null);
            fo.close();
            return f.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
