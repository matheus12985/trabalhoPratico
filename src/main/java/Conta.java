public abstract class Conta {
    protected Cliente cliente;
    protected double saldo;

    public Conta(Cliente cliente) {
        this.cliente = cliente;
        this.saldo = 0;
    }

    public double getSaldo() {
        return saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public abstract boolean sacar(double valor);
    public abstract void depositar(double valor);
    public abstract void aplicarRendimentoMensal();
}
