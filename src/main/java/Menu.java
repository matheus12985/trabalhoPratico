
import java.util.*;

public class Menu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Cliente> clientes = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nXuBank - Menu Principal");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Criar Conta");
            System.out.println("3. Selecionar Conta e Operar");
            System.out.println("4. Aplicar Rendimentos Mensais");
            System.out.println("5. Relatório de Saldos");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> criarConta();
                case 3 -> operarConta();
                case 4 -> aplicarRendimentos();
                case 5 -> relatorioSaldos();
                case 0 -> {
                    System.out.println("Encerrando o sistema...");
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void cadastrarCliente() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        clientes.add(new Cliente(nome, cpf, senha));
        System.out.println("Cliente cadastrado.");
    }

    private static Cliente buscarCliente() {
        System.out.print("CPF do cliente: ");
        String cpf = scanner.nextLine();
        return clientes.stream().filter(c -> c.getCpf().equals(cpf)).findFirst().orElse(null);
    }

    private static void criarConta() {
        Cliente cliente = buscarCliente();
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        System.out.println("Tipo de conta: 1-Corrente, 2-Poupança, 3-Renda Fixa, 4-Investimento");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        Conta novaConta = switch (tipo) {
            case 1 -> new ContaCorrente(cliente, 300.0);
            case 2 -> new ContaPoupanca(cliente);
            case 3 -> new ContaRendaFixa(cliente);
            case 4 -> new ContaInvestimento(cliente);
            default -> null;
        };

        if (novaConta != null) {
            cliente.adicionarConta(novaConta);
            System.out.println("Conta criada com sucesso.");
        } else {
            System.out.println("Tipo de conta inválido.");
        }
    }

    private static void operarConta() {
        Cliente cliente = buscarCliente();
        if (cliente == null || cliente.getContas().isEmpty()) {
            System.out.println("Cliente não encontrado ou não possui contas.");
            return;
        }

        System.out.println("Contas disponíveis:");
        for (int i = 0; i < cliente.getContas().size(); i++) {
            System.out.println(i + " - " + cliente.getContas().get(i).getClass().getSimpleName());
        }

        System.out.print("Escolha a conta: ");
        int idx = scanner.nextInt();
        scanner.nextLine();
        Conta conta = cliente.getContas().get(idx);

        System.out.println("1. Depositar | 2. Sacar | 3. Ver saldo");
        int acao = scanner.nextInt();
        scanner.nextLine();

        switch (acao) {
            case 1 -> {
                System.out.print("Valor: ");
                double valor = scanner.nextDouble();
                conta.depositar(valor);
                System.out.println("Depósito realizado.");
            }
            case 2 -> {
                System.out.print("Valor: ");
                double valor = scanner.nextDouble();
                boolean sucesso = conta.sacar(valor);
                System.out.println(sucesso ? "Saque realizado." : "Saque não permitido.");
            }
            case 3 -> System.out.println("Saldo atual: R$" + conta.getSaldo());
            default -> System.out.println("Opção inválida.");
        }
    }

    private static void aplicarRendimentos() {
        for (Cliente cliente : clientes) {
            for (Conta conta : cliente.getContas()) {
                if (conta instanceof ContaPoupanca cp) {
                    cp.aplicarRendimentoMensal();
                } else if (conta instanceof ContaRendaFixa rf) {
                    rf.aplicarRendimentoMensal();
                } else if (conta instanceof ContaInvestimento ci) {
                    ci.aplicarRendimentoMensal();
                }
            }
        }
        System.out.println("Rendimentos aplicados.");
    }

    private static void relatorioSaldos() {
        Map<String, List<Double>> saldos = new HashMap<>();
        double maior = Double.NEGATIVE_INFINITY;
        double menor = Double.POSITIVE_INFINITY;

        for (Cliente cliente : clientes) {
            for (Conta conta : cliente.getContas()) {
                String tipo = conta.getClass().getSimpleName();
                saldos.computeIfAbsent(tipo, k -> new ArrayList<>()).add(conta.getSaldo());

                double saldo = conta.getSaldo();
                if (saldo > maior) maior = saldo;
                if (saldo < menor) menor = saldo;
            }
        }

        System.out.println("Relatório de saldos por tipo de conta:");
        saldos.forEach((tipo, lista) -> {
            double total = lista.stream().mapToDouble(Double::doubleValue).sum();
            double media = total / lista.size();
            System.out.printf("%s - Total: R$%.2f | Média: R$%.2f%n", tipo, total, media);
        });

        System.out.printf("Maior saldo: R$%.2f | Menor saldo: R$%.2f%n", maior, menor);
    }
}
