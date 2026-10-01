Proposta de Atividade: Projeto Multiverse Radar 🪐
Contexto da Missão
Você foi contratado pela Cidadela para criar o Multiverse Radar, um aplicativo móvel moderno em Jetpack Compose que cataloga personagens do multiverso de Rick and Morty.
O maior desafio: os cientistas viajam por dimensões sem sinal de internet! Por isso, o app precisa funcionar com a arquitetura Offline-First:
O app consome a API oficial do Rick and Morty.
Os dados obtidos são gravados no banco de dados local (Room).
A interface gráfica (Jetpack Compose) escuta exclusivamente o banco local através de fluxos reativos (Flow).
Se a internet cair ou o app for colocado em Modo Avião, os personagens já salvos continuam disponíveis na tela.
Tema & API Utilizada
API: The Rick and Morty API
Endpoint: GET [https://rickandmortyapi.com/api/character](https://rickandmortyapi.com/api/character)
Vantagens: 100% gratuita, sem necessidade de token/cadastro, resposta rápida e JSON simples contendo id, name, species, status e image.
Requisitos Obrigatórios
1. Kotlin & POO (Programação Orientada a Objetos)
Modelagem: Uso de data class para representar os dados da API e do banco local.
Mapeamento: Prática de encapsulamento e separação de responsabilidades (conversão segura do modelo de rede para a entidade de banco).
Gestão de Estados: Criação de uma sealed interface para representar o estado da sincronização (Idle, Loading, Success, Error).
2. Persistência com Room
Criação de uma @Entity simples (ex: CharacterEntity com id, name, species, imageUrl).
Criação de um @Dao com:
Uma função que retorna Flow<List<CharacterEntity>> para observação em tempo real.
Uma função suspend com @Upsert ou @Insert(onConflict = OnConflictStrategy.REPLACE) para sincronização em lote.
Definição da @Database.
3. Consumo de API (Retrofit)
Interface com função suspend buscando a lista de personagens.
Conversor Gson ou Moshi para desserialização do JSON.
4. Arquitetura: Repository como Single Source of Truth (SSOT)
O Repository deve conter:
O fluxo Flow<List<CharacterEntity>> que expõe os dados do Room para a ViewModel.
Uma função suspend fun syncCharacters() que faz a requisição na API e insere a lista diretamente no Room.
Regra de Ouro: A ViewModel e a UI nunca consom a API diretamente.
5. Interface com Jetpack Compose
Tela Única:
TopAppBar com título estilizado e um botão (ou IconButton) de Sincronizar (Refresh).
Exibição de indicador de carregamento (CircularProgressIndicator) quando o estado for Loading.
Mensagem amigável de erro caso a sincronização falhe (via Snackbar ou Text de aviso).
LazyColumn exibindo os personagens em Cards elegantes (mostrando imagem com Coil, nome e espécie).
A UI deve coletar o StateFlow da ViewModel usando collectAsState() ou collectAsStateWithLifecycle().
Critérios de Validação Prática (Testes em Sala)
Teste do Modo Avião: O aluno deve abrir o app, sincronizar uma vez, ativar o Modo Avião no emulador/aparelho, fechar o app e abri-lo novamente: a lista salva deve continuar visível.
Resiliência a Falhas: Com o Modo Avião ligado, clicar no botão "Sincronizar". O app deve exibir um aviso de erro de conexão, sem apagar os itens já carregados no banco.