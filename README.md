
# 🧠 Projeto: Avaliador de Expressões Lógicas 

## 📌 Descrição

Este projeto Java tem como objetivo **receber, validar e resolver expressões lógicas proposicionais**, suportando operadores como:

- `AND`, `OR`
- `->` (implicação)
- `<->` (bicondicional)
- `↑` (nand), `↓` (nor), `⊕` (xor)
- Negação com `~`
- Parênteses para controle de precedência

O sistema ainda **verifica erros de digitação e estrutura**, e sugere correção ao usuário, garantindo que a entrada esteja bem formada antes de ser avaliada.

---

## ✅ Funcionalidades

- Validação de variáveis `(A-E, G-U; Trata "V" como verdadeiro e "F" como falso; Caractéres seguintes a "V" são usados como futuramente no programa e não devem ser utilizados como variáveis)` e conectivos
- Correção automática de parênteses faltantes
- Tratamento de negações e precedência com parênteses
- Substituição de expressões intermediárias por variáveis auxiliares `( W, X, Y, ...)`
- Exibição do resultado final da expressão

---

## 🖥️ Como usar

1. Compile todos os arquivos `.java`:
   ```bash
   javac Main.java
   ```

2. Execute o programa:
   ```bash
   java Main
   ```

3. Insira uma expressão lógica quando solicitado.

---

## 🧾 Formato esperado do input

- **Cada elemento da expressão deve estar separado por um espaço.**
- **As variáveis** devem ser letras únicas (`A`, `B`, `C`, ...).
- Conectivos aceitos **(devem ser escritos como mostrado, caso contrário o programa irá considerar erro de escrita)**: 
   - `AND, OR` 
   - `-> (implicação)`
   - `<-> (bicondicional)`  
   - `↑ (nand), ↓ (nor), ⊕ (xor)`
- **Parênteses**, conectivos e operadores devem estar isolados por espaços.
- Caso seja necessário fazer a negação de uma variável, deverá ser feita **entre parênteses**.

### ✅ Exemplos válido:

```
( A AND B ) -> ( C OR ~ ( D ) )
( a and b ) -> ( c or ( d        // o programa aceita letras minúsculas e fecha os parêntes abertos automáticamente ao final da expressão.
```
### ❌ Exemplos inválidos:

```
AAND B        // "AAND" não é conectivo nem variável
A->B          // deve ser "A -> B"
(A AND B)     // sem espaços entre parênteses e conteúdo
~Q OR B       // a negação deve ser feita antes de um parênteses
```

> O sistema irá pedir uma nova entrada se houver algum erro de digitação.

---

## 📤 Resultado 

Para a entrada:
```
( A AND B ) -> ( C OR ~ ( D ) )
```

O sistema:
1. Valida a entrada
2. Corrige parênteses se necessário
3. Avalia a expressão lógica
4. Exibe o resultado final (`V`, `F`, `uma váriavel específica ou a negação da mesma se possível`, ou uma sequência de atribuições intermediárias como `W = ...`)

---

## 📁 Estrutura do projeto

- `Main.java` — ponto de entrada do programa
- `Correcao.java` — validação, correção e resolução da expressão
- `MetodosPrincipaisCorrecao.java` — lógica principal de avaliação
- `MetodosBaseCorrecao.java` — funções auxiliares
- `Input.java` — controle centralizado da entrada do usuário
- `Funcoes.java` — funções utilitárias
- `Tabela.java` — operadores e valores lógicos