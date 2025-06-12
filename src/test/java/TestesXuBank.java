
public class TestesXuBank {
    public static void main(String[] args) {
        Cliente cliente = new Cliente("Teste", "00000000000", "senha");

        ContaCorrente cc = new ContaCorrente(cliente, 300.0);
        cc.depositar(1000);
        assert cc.getSaldo() == 1000;
        cc.sacar(200);
        assert cc.getSaldo() == 800;

        ContaPoupanca cp = new ContaPoupanca(cliente);
        cp.depositar(1000);
        cp.aplicarRendimentoMensal(); // +0.6%
        assert cp.getSaldo() > 1000;

        ContaRendaFixa rf = new ContaRendaFixa(cliente);
        rf.depositar(1000);
        rf.aplicarRendimentoMensal();
        double saldoAntes = rf.getSaldo();
        rf.sacar(100);
        assert rf.getSaldo() < saldoAntes;

        ContaInvestimento ci = new ContaInvestimento(cliente);
        ci.depositar(1000);
        ci.aplicarRendimentoMensal();
        double saldoInicial = ci.getSaldo();
        ci.sacar(50);
        assert ci.getSaldo() < saldoInicial;

        System.out.println("Todos os testes foram executados com sucesso.");
    }
}
