## Parte B: Questões dissertativas

### B1. O que é persistência de dados? O que aconteceria se os produtos fossem guardados apenas em uma MutableList dentro da MainActivity?

Resposta: Persistência de dados é basicamente manter os dados mesmo com o encerramento da aplicação. Os dados ficam armazenados localmente, tipo o SQLite. Se fosse só numa MutableList, quando fechasse o app os produtos sumiam.

### B2. Qual é a diferença entre usar o SQLite diretamente e usar o Room? Cite pelo menos duas vantagens do Room.

Resposta: O Room já vem com boa parte pronta do schema e tem integração por anotações. O SQLite é bom, mas nele você tem que gerar todo o escopo do banco na mão. O Room também já dá erro de tabela e de query quando compila, antes de rodar o app.

### B3. Explique o papel de cada uma das três peças do Room: Entity, DAO e Database. Como elas se relacionam no seu projeto?

Resposta: A Entity é a tabela, com os atributos de produto. O DAO é o Data Access Object, faz essa ponte com o banco. E o Database é o banco de fato, que junta a Entity e o DAO, grava os itens e disponibiliza a consulta.

### B4. Por que o Android não permite acessar o banco na thread principal? O que é um ANR? Como suspend e lifecycleScope.launch resolvem esse problema?

Resposta: Basicamente é uma segurança, pois o banco pode demorar, ter algum travamento, e dessa forma obriga a ficar numa thread, num caminho diferente, e rodar no seu tempo. Se não fizer esse ajuste, o app fecha quando vai acessar o banco. O ANR é quando o app trava e o Android avisa que ele não está respondendo. O suspend e o lifecycleScope.launch fazem o acesso ao banco rodar em segundo plano, sem travar a tela.

### B5. O que faz o fallbackToDestructiveMigration()? Por que ele é perigoso em um app publicado na Play Store? O que seria uma migração?

Resposta: Quando um banco é gerado, por baixo dos panos muita coisa é feita, como geração de tabelas e relacionamentos de chaves, constraints e arquivos gerados pelo próprio banco com essa estrutura. O fallbackToDestructiveMigration() limpa e zera essa estrutura e recria novamente. É muito perigoso subir com esse comando, pois pode destruir toda a estrutura e perder os dados. A migração é mudar o banco de uma versão pra outra sem perder os dados.

### B6. Qual é a vantagem do ViewBinding em relação ao findViewById? Que tipo de erro ele ajuda a evitar?

Resposta: Isso salva muito, não tem que ficar indo na view, instanciando o componente e colocando ele numa variável. Com o binding eu consigo acessar diretamente os componentes da view. Também evita erro de pegar um id que não existe na tela.

### B7. Por que em apps maiores é recomendado criar o banco como Singleton? O que poderia acontecer se várias telas criassem cada uma o seu banco?

Resposta: Criar o banco é pesado, então o melhor é ter uma instância só pro app inteiro. Se cada tela criasse o seu banco, ia ter várias conexões abertas no mesmo arquivo, gastando memória e podendo dar conflito nos dados.

### B8. Reflexão pessoal: principais dificuldades e um app real em que o Room seria útil.

Resposta: Foram muitos itens, isso que demorou, porém em dificuldade foi legal de pegar as queries no DAO. Tivemos que lembrar o RecyclerView pra fazer o desafio extra e a exclusão por item, mas ficou show. Um app que o Room seria útil é um de controle de estoque, pra consultar os produtos mesmo sem internet.
