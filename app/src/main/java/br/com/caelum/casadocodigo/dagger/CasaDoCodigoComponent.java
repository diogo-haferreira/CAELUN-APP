package br.com.caelum.casadocodigo.dagger;

import javax.inject.Singleton;

import br.com.caelum.casadocodigo.activity.CarrinhoActivity;
import br.com.caelum.casadocodigo.fragments.DetalheLivroFragment;
import dagger.Component;

@Component(modules = CasaDoCodigoModulo.class)
@Singleton
public interface CasaDoCodigoComponent {
    void inject(CarrinhoActivity carrinhoActivity);

    void inject(DetalheLivroFragment detalheLivroFragment);
}
