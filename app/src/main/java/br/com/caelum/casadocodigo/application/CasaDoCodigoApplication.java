package br.com.caelum.casadocodigo.application;

import android.app.Application;

import br.com.caelum.casadocodigo.dagger.CasaDoCodigoComponent;
import br.com.caelum.casadocodigo.dagger.DaggerCasaDoCodigoComponent;

public class CasaDoCodigoApplication extends Application {

    private static CasaDoCodigoApplication app;
    private CasaDoCodigoComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        component = DaggerCasaDoCodigoComponent.create();
    }

    public CasaDoCodigoComponent getComponent() {
        return component;
    }

    public static CasaDoCodigoApplication getInstance() {
        return app;
    }
}
