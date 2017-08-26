package com.example.diego.topchat.Adapter;

import android.net.wifi.p2p.WifiP2pManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.diego.topchat.R;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Diego on 26/08/2017.
 */

public class AdaptadorMensagens extends BaseAdapter {

    private ArrayList<JSONObject> mensagens;
    private TextView usuario;
    private TextView mensagem;

    public AdaptadorMensagens(ArrayList<JSONObject> mensagens) {
        this.mensagens = mensagens;
    }

    @Override
    public int getCount() {
        return mensagens.size();
    }

    @Override
    public Object getItem(int i) {
        return mensagens.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        JSONObject jsonObject = mensagens.get(i);
        String user = "";
        String nome = "";
        String texto_mensagem = "";


        try {
            user = jsonObject.getString("sender");
            nome = jsonObject.getString("name");
            texto_mensagem = jsonObject.getString("message");

        }catch (Exception e){

        }


        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        if(user.equals("1")){ //Recebida
            view = inflater.inflate(R.layout.item_recebido, viewGroup, false);
            usuario = (TextView) view.findViewById(R.id.textViewRecebidoUser);
            mensagem = (TextView) view.findViewById(R.id.textViewRecebidoMensagem);

            usuario.setText(nome);
        }else {
            view = inflater.inflate(R.layout.item_enviado, viewGroup, false);
            mensagem = (TextView) view.findViewById(R.id.textViewEnviadoMensagem);
        }

        mensagem.setText(texto_mensagem);

        return view;
    }
}
