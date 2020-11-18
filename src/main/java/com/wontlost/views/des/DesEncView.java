package com.wontlost.views.des;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.wontlost.ckeditor.Constants.*;
import com.wontlost.ckeditor.VaadinCKEditor;
import com.wontlost.ckeditor.VaadinCKEditorBuilder;
import com.wontlost.cypher.EncryptData;
import com.wontlost.cypher.des.DESCypher;
import com.wontlost.cypher.des.DESData;
import com.wontlost.utils.Cipher;
import com.wontlost.views.MainView;

import java.util.Objects;

import static com.wontlost.utils.Constant.*;

@Route(value = PAGE_DESENC_EDITOR, layout = MainView.class)
@RouteAlias(value = PAGE_ROOT, layout = MainView.class)
@PageTitle(TITLE_DESENC_EDITOR)
public class DesEncView extends VerticalLayout {

    DesEncView() {
        super();
        VaadinCKEditor classicEditor = new VaadinCKEditorBuilder().createVaadinCKEditor();

        VaadinCKEditor encodeEditor = new VaadinCKEditorBuilder().with(builder -> {
            builder.editorData = "";
            builder.editorType = EditorType.INLINE;
            builder.readOnly = true;
        }).createVaadinCKEditor();

        add(classicEditor);

        TextField key = new TextField("Set the key: ");
        add(key);

        add(new Label(""));
        Button encode = new Button("Encode");
        encode.addClickListener(event -> {
            if(key.getValue()==null || key.getValue().isEmpty()) {
                Cipher.notification("Key cannot be empty.").open();
                return;
            }
            String classicValue = classicEditor.getValue();
            try {
                DESData encryptedData = DESCypher.encrypt(classicValue, key.getValue().toCharArray());
                EncryptData.desData().put("desData", encryptedData);
                encodeEditor.setValue(Objects.requireNonNull(encryptedData).encryptedString);
            } catch (Exception e) {
                Cipher.notification("Key is not correct!!!").open();
            }
        });
        add(encode);
        add(new Label(""));
        add(encodeEditor);

        setAlignItems(Alignment.CENTER);
    }

}
