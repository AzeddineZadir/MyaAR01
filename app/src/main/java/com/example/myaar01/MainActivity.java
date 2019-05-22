package com.example.myaar01;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // le fragments
    ArFragment arFragment;
    private ModelRenderable horsRenderable,captureRenderable,fauteill1Renderable,chicrenderable;
    ImageView horse,capture ,fauteill1;
    View arrayView[];
    ViewRenderable name_animal ;
    int selected= 1 ; //defaulte hors is choose

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arFragment=(ArFragment)getSupportFragmentManager().findFragmentById(R.id.sceneforme_ux_fragment);
        // view
        horse =(ImageView)findViewById(R.id.hors);
        capture =(ImageView)findViewById(R.id.capture);
        fauteill1 =(ImageView)findViewById(R.id.fauteill1);

        setArrayView();
        setClickLIstener();

        setupModel();
        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                // on ajoute un model lorsque lutilisateur appuis su r le plant

                    Anchor anchor =hitResult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());
                    createModel(anchorNode,selected);

            }
        });

    }

    private void setupModel() {
        ModelRenderable.builder()
                .setSource(this,R.raw.chic196)
                .build().thenAccept(renderable ->chicrenderable=renderable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this, "on a pas pue charger le model horse", Toast.LENGTH_SHORT).show();
                            return null;
                        }
                );

        ModelRenderable.builder()
                .setSource(this,R.raw.model)
                .build().thenAccept(renderable ->captureRenderable=renderable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this, "on a pas pue charger le model capture", Toast.LENGTH_SHORT).show();
                            return null;
                        }
                );

        ModelRenderable.builder()
                .setSource(this,R.raw.fauteuill1)
                .build().thenAccept(renderable ->fauteill1Renderable=renderable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this, "on a pas pue charger le model fauteille 1", Toast.LENGTH_SHORT).show();
                            return null;
                        }
                );

    }

    private void createModel(AnchorNode anchorNode, int selected) {
        if (selected==1){

            TransformableNode horse = new TransformableNode(arFragment.getTransformationSystem());
            horse.setParent(anchorNode);
            horse.setRenderable(chicrenderable);
            horse.select();

        }

        if (selected==2){

            TransformableNode capture = new TransformableNode(arFragment.getTransformationSystem());
            capture.setParent(anchorNode);
            capture.setRenderable(captureRenderable);
            capture.select();

        }
        if (selected==3){

            TransformableNode capture = new TransformableNode(arFragment.getTransformationSystem());
            capture.setParent(anchorNode);
            capture.setRenderable(fauteill1Renderable);
            capture.select();

        }
    }


    private void setClickLIstener() {
        for (int i = 0 ;i<arrayView.length; i++){
            arrayView[i].setOnClickListener(this);
        }
    }

    private void setArrayView() {
        arrayView =new View[]{horse,capture,fauteill1};
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.hors) {
            selected = 1;
            setBackground(view.getId());
        }else if (view.getId()== R.id.capture) {
            selected = 2;
            setBackground(view.getId());
        }
        else if (view.getId()== R.id.fauteill1) {
            selected = 3;
            setBackground(view.getId());
        }
    }

    private void setBackground(int id) {
    for (int i=0 ; i<arrayView.length;i++){

        if (arrayView[i].getId()==id){
        arrayView[i].setBackgroundColor(Color.parseColor("#80333639")); // set background for selected item
        }else {
            arrayView[i].setBackgroundColor(Color.TRANSPARENT ); // set background for unselected item

        }
    }
    }
}
