package exemplo.listacontato.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import exemplo.listacontato.R;
import exemplo.listacontato.model.Contato;

public class ContatoAdapter extends RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder>{

    private List<Contato> list;

    public ContatoAdapter(List<Contato> list){
        this.list = list;
    }

    @NonNull
    @Override
    public ContatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.celula_contato, parent, false);
        return new ContatoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContatoViewHolder holder, int position) {
        Contato contato = list.get(position);
        holder.nome.setText(contato.getNome());
        holder.telefone.setText(contato.getTelefone());
        holder.email.setText(contato.getEmail());
        File file = new File(contato.getFoto());
        if(file.exists()){
            Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
            holder.foto.setImageBitmap(bm);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ContatoViewHolder extends RecyclerView.ViewHolder{
      ImageView foto;
      TextView nome;
      TextView email;
      TextView telefone;

        public ContatoViewHolder(View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.contato_item_foto);
            nome = itemView.findViewById(R.id.contato_item_nome);
            email = itemView.findViewById(R.id.contato_item_email);
            telefone = itemView.findViewById(R.id.contato_item_telefone);

        }
    }
}
