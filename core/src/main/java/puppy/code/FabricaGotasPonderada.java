package puppy.code;

import com.badlogic.gdx.math.MathUtils;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Supplier;

public final class FabricaGotasPonderada implements FabricaGotas {
    private final NavigableMap<Integer, Supplier<ObjetoCaida>> opciones
            = new TreeMap<>();
    private int pesoTotal;

    public FabricaGotasPonderada registrar(int peso,
            Supplier<ObjetoCaida> constructor) {
        Objects.requireNonNull(constructor);
        if (peso <= 0 || peso > Integer.MAX_VALUE - pesoTotal) {
            throw new IllegalArgumentException("Peso invalido");
        }
        pesoTotal += peso;
        opciones.put(pesoTotal, constructor);
        return this;
    }

    @Override
    public ObjetoCaida crear() {
        if (opciones.isEmpty()) {
            throw new IllegalStateException("Faltan tipos de gota");
        }
        int sorteo = MathUtils.random(1, pesoTotal);
        Supplier<ObjetoCaida> constructor = opciones.ceilingEntry(sorteo)
                .getValue();
        return Objects.requireNonNull(constructor.get());
    }
}
