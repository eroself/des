package com.wontlost.views.des;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.wontlost.ckeditor.Constants.*;
import com.wontlost.ckeditor.VaadinCKEditor;
import com.wontlost.ckeditor.VaadinCKEditorBuilder;
import com.wontlost.cypher.EncryptData;
import com.wontlost.cypher.des.DESCypher;
import com.wontlost.cypher.des.DESData;
import com.wontlost.utils.Cipher;
import com.wontlost.views.MainView;
import org.jsoup.Jsoup;

import static com.wontlost.utils.Constant.*;

@Route(value = PAGE_DESDEC_EDITOR, layout = MainView.class)
@PageTitle(TITLE_DESDEC_EDITOR)
public class DesDecView extends VerticalLayout {

    DesDecView() {
        super();
        VaadinCKEditor classicEditor = new VaadinCKEditorBuilder().createVaadinCKEditor();

        VaadinCKEditor decodeEditor = new VaadinCKEditorBuilder().with(builder -> {
            builder.editorData = "";
            builder.editorType = EditorType.INLINE;
            builder.readOnly = true;
        }).createVaadinCKEditor();

        add(classicEditor);

        TextField key = new TextField("Set the key: ");
        add(key);

        add(new Label(""));
        Button decode = new Button("Decode");
        decode.addClickListener(event -> {
            if(key.getValue()==null || key.getValue().isEmpty()) {
                Cipher.notification("Key cannot be empty.").open();
                return;
            }
            String classicValue = Jsoup.parse(classicEditor.getValue()).text();
            byte[] salt = EncryptData.desData().get("desData")!=null? EncryptData.desData().get("desData").salt:new byte[]{};
            DESData encryptedData = new DESData(salt, classicValue);
            try {
                String decodeValue = DESCypher.decrypt(encryptedData, key.getValue().toCharArray());
                decodeEditor.setValue(decodeValue);
            } catch (Exception e) {
                Cipher.notification("Key is not correct!!!").open();
            }
        });
        add(decode);
        add(new Label(""));
        add(decodeEditor);

        setAlignItems(Alignment.CENTER);
    }

}
