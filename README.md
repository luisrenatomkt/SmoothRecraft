# Smooth Recraft (Minecraft 1.8.9 / Forge 11.15.1.2318)

Processa a fila de entrada da GUI de containers a cada frame (e nao so a cada tick de 50 ms),
reduzindo a latencia dos cliques no recraft. 100% manual: sem macros, sem autoclick, sem atrasos.

- Ativado por padrao.
- Comando: `/smoothrecraft [on|off|toggle|status]`
- Config: `config/smoothrecraft.cfg`

## Compilar pelo GitHub
1. Suba todos os arquivos deste projeto (incluindo a pasta `.github`) em um repositorio.
2. Aba **Actions** > **Build SmoothRecraft** > aguarde ficar verde.
3. Abra a execucao e baixe o artefato **SmoothRecraft-1.8.9** (contem o `.jar`).
4. Coloque o `.jar` em `.minecraft/mods/`.
