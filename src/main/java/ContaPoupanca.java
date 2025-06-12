public class ContaPoupanca extends Conta {
    public ContaPoupanca(Cliente cliente) {
        super(cliente);
    }

    @Override
    public boolean sacar(double valor) {
        if (valor <= saldo) {
            saldo -= valor;
            return true;
        }
        return false;
    }

    @Override
    public void depositar(double valor) {
        saldo += valor;
    }

    @Override
    public void aplicarRendimentoMensal() {
        saldo *= 1.01; // Rendimento de 1%
    }
}
