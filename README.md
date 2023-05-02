# Documentação da API do Aplicativo Mia

Esta API foi criada para auxiliar no processo de lógica do aplicativo Mia. A API gerencia Usuários e Tarefas, permitindo a criação, atualização, exclusão e recuperação de informações relacionadas a essas entidades.

## Entidades

### Usuario
- `nome` (string): Nome do usuário.
- `email` (string): Endereço de e-mail do usuário.
- `senha` (string): Senha do usuário.
- `data_cadastro` (datetime): Data e hora em que o usuário foi cadastrado.

### Tarefa
- `titulo` (string): Título da tarefa.
- `passos` (array): Lista de passos detalhados para a execução da tarefa. Cada passo é um objeto que contém um texto gerado pelo GPT-3.
- `aplicativo` (string, obrigatório): Nome do aplicativo a qual a tarefa se refere.

## Endpoints

### Usuários

#### `POST /usuarios`
Cria um novo usuário.

**Parâmetros do corpo:**
- `nome` (string, obrigatório): Nome do usuário.
- `email` (string, obrigatório): Endereço de e-mail do usuário.
- `senha` (string, obrigatório): Senha do usuário.

**Respostas:**
- `201 Created`: Usuário criado com sucesso.
- `400 Bad Request`: Parâmetros inválidos.

#### `GET /usuarios`
Lista todos os usuários cadastrados.

**Respostas:**
- `200 OK`: Retorna uma lista de usuários.
- `404 Not Found`: Nenhum usuário encontrado.

#### `GET /usuarios/{id}`
Recupera informações sobre um usuário específico.

**Parâmetros do caminho:**
- `id` (integer, obrigatório): ID do usuário.

**Respostas:**
- `200 OK`: Retorna informações do usuário.
- `404 Not Found`: Usuário não encontrado.

#### `PUT /usuarios/{id}`
Atualiza informações de um usuário específico.

**Parâmetros do caminho:**
- `id` (integer, obrigatório): ID do usuário.

**Parâmetros do corpo:**
- `nome` (string, opcional): Nome do usuário.
- `email` (string, opcional): Endereço de e-mail do usuário.
- `senha` (string, opcional): Senha do usuário.

**Respostas:**
- `200 OK`: Usuário atualizado com sucesso.
- `400 Bad Request`: Parâmetros inválidos.
- `404 Not Found`: Usuário não encontrado.

#### `DELETE /usuarios/{id}`
Exclui um usuário específico.

**Parâmetros do caminho:**
- `id` (integer, obrigatório): ID do usuário.

**Respostas:**
- `204 No Content`: Usuário excluído com sucesso.
- `404 Not Found`: Usuário não encontrado.

### Tarefas

#### `POST /tarefas`
Cria uma nova tarefa.

**Parâmetros do corpo:**
- `titulo` (string, obrigatório): Título da tarefa.
- `passos` (array, obrigatório): Lista de passos da tarefa.
- `aplicativo` (string, obrigatório): Nome do aplicativo a qual a tarefa se refere.

**Respostas:**
- `201 Created`: Tarefa criada com sucesso.
- `400 Bad Request`: Parâmetros inválidos.

#### `GET /tarefas`
Lista todas as tarefas cadastradas.

**Respostas:**
- `200 OK`: Retorna uma lista de tarefas.
- `404 Not Found`: Nenhuma tarefa encontrada.

#### `GET /tarefas/{id}`
Recupera informações sobre uma tarefa específica.

**Parâmetros do caminho:**
- `id` (integer, obrigatório): ID da tarefa.

**Respostas:**
- `200 OK`: Retorna informações da tarefa.
- `404 Not Found`: Tarefa não encontrada.

#### `PUT /tarefas/{id}`
Atualiza informações de uma tarefa específica.

**Parâmetros do caminho:**
- `id` (integer, obrigatório): ID da tarefa.

**Parâmetros do corpo:**
- `titulo` (string, opcional): Título da tarefa.
- `passos` (array, opcional): Lista de passos da tarefa.
- `aplicativo` (string, obrigatório): Nome do aplicativo a qual a tarefa se refere.

**Respostas:**
- `200 OK`: Tarefa atualizada com sucesso.
- `400 Bad Request`: Parâmetros inválidos.
- `404 Not Found`: Tarefa não encontrada.

#### `DELETE /tarefas/{id}`
Exclui uma tarefa específica.

**Parâmetros do caminho:**
- `id` (integer, obrigatório): ID da tarefa.

**Respostas:**
- `204 No Content`: Tarefa excluída com sucesso.
- `404 Not Found`: Tarefa não encontrada.

## Autenticação e Autorização

Para garantir a segurança e privacidade dos dados dos usuários, a API utiliza um sistema de autenticação e autorização baseado em tokens. Um token de acesso é gerado ao fazer login com um e-mail e senha válidos.

### `POST /auth/login`
Autentica um usuário e retorna um token de acesso.

**Parâmetros do corpo:**
- `email` (string, obrigatório): Endereço de e-mail do usuário.
- `senha` (string, obrigatório): Senha do usuário.

**Respostas:**
- `200 OK`: Retorna um token de acesso.
- `400 Bad Request`: Parâmetros inválidos.
- `401 Unauthorized`: Credenciais inválidas.

### Uso do token

O token de acesso deve ser incluído no cabeçalho `Authorization` de cada requisição feita à API. O token deve ser precedido pelo termo "Bearer", conforme o exemplo abaixo:

`Authorization`: Bearer `SEU_TOKEN_AQUI`

Os endpoints de gerenciamento de tarefas exigem que o usuário esteja autenticado e autorizado para acessá-los. Caso contrário, a API retornará um erro `401 Unauthorized`.



