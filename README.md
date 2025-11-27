## 🔧 Funcionalidades pendentes

O principal Objetivo é enxugar o código o máximo possível, para isso, será utilizado, além dos conceitos adquiridos em sala de aula,
o conceito de arquitetura em camadas MVC;

- [X] Utilizando polimorfismo - elimina-se o uso de "ifs" para as classes;
- [X] Utilizando o gerenciamento de arrayList ou hashMap por na camada repository;
- [x] Utilizando formatação de moedas melhoramos a interação do usuário com o sistema.
- [x] Unificando os tipos de contas bancarias em um só lista do tipo hashMap facilitamos a distinção
das contas pelo seu número de conta, garantindo que exista uma conta para cada número de conta.

## Resumo do MVC ##

## Implementação com MVC

- A busca pela organização de um código é um dos principais motivos pela adoção de uma arquitetura para auxiliar tanto nessa organização quanto na manutenção do código.
Sendo assim, neste projeto foi utilizado o padrão do MVC (Model->View->Controller) para melhora-lo.

# view 
    - Camada responsável pelo interface gráfica: Netbeans com Swing;
    - Essa camada não tem o poder de tomar decisões, essa responsabilidade é do controller;

# Controller
    - O cérebro da aplicação. Ele é o responsável por gerenciar os fluxos das views;
    - Ele decide se vai abrir ou não uma janela, se vai chamar um metodo do service, ele é quem decide!
    - Ele também é responsável por se conectar a um service;

# Service
    - Essa camada é a responsável pelas regras de négocio, saldo negativo, positivo, Transferencias entre contas, etc. 
    - Ele se conecta com o repository.

# Repository
    - Camada responsável pelo armazenamento dos dados, neste projeto as listas com os dados das contas;
    - Futuramente com os dados armazenados em um banco de dados.

Cada camada tem sua responsabilidade, em outras palavra, as demais devem confiar nela para evitar redundancia de código.

## projeto funcionando para todas as operações;
 - [x] verificar possiveis incosistências no projeto que violem a arquitetura das camadas e corrigir;
