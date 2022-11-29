# E-commerce
Esse projeto faz parte do aprendizado do curso de Android com Kotlin no qual foi sugerido a criação e simulação de um aplicativo para um E-commerce.
Nele foi desenvolvido um aplicativo que possui a funcionalidade de listagem e cadastro de produtos que poderiam ser comercializado pelo empresa. O projeto permite cadastrar, alterar e remover produtos com imagem, nome, descrição e valor. Os produtos salvos são apresentados em uma lista e podem ser visualizados ao realizar o clique.


# Técnicas e Tecnologias utilizadas:
- Kotlin ;
- Activities;
-Layout para Activities;
  - TextView
  - RecyclerView
  - ConstraintLayout
  - EditTex
  - Button
- Binding View;
- Listener para cliques;
- AndroidX;
  - AppCompactActivity
- Jetpack Room: lib para persistência de dados em banco de dados interno com SQLite
- Entidade: definição da tabela que será criada no banco de dados
- DAO: definição dos comportamentos com o banco de dados
  - Comportamentos definidos: inserção, alteração, remoção e consultas de todos os registros e com filtro
- Database: configuração para criar a conexão com o banco de dados conversor de tipo: converter um tipo complexo para um tipo compatível com o SQLite
- Menu de opções: menu para editar e remover
- Extras: técnica para enviar e receber informações entre Activities
- Inicialização lateinit e lazy: técnicas para criar propriedades em Activities que não podem ser inicializadas na construção da Activity


# Ilustração
