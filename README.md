# Smooth Recraft (Minecraft 1.8.9 / Forge 11.15.1.2318)

1. Processa a fila de entrada da GUI de containers a cada frame (e nao so a cada tick de 50 ms),
   reduzindo a latencia dos cliques no recraft.
2. Congela visualmente o cenario enquanto um container esta aberto (so o desenho; o jogo continua
   rodando), deixando o FPS alto e estavel e sem rastro no item carregado.

100% manual: sem macros, sem autoclick, sem atrasos.

- Ativado por padrao.
- Comandos: `/smoothrecraft [on|off|toggle|status]` e `/smoothrecraft freeze [on|off|toggle]`
- Config: `config/smoothrecraft.cfg` (`enabled`, `affectAllScreens`, `freezeWorld`)

## Compilar pelo GitHub
1. Suba todos os arquivos (incluindo a pasta `.github`) em um repositorio.
2. Aba **Actions** > **Build SmoothRecraft** > aguarde ficar verde.
3. Baixe o artefato **SmoothRecraft-1.8.9** (contem o `.jar`).
4. Coloque o `.jar` em `.minecraft/mods/`.
