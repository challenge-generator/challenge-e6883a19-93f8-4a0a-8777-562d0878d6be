# Optimización de recursos en agentes de IA

La empresa PragmaBank ha implementado un agente de IA para procesar transacciones financieras. Este agente consume una cantidad significativa de recursos de CPU y memoria. Tu tarea es optimizar el uso de estos recursos para que el agente pueda manejar un mayor volumen de transacciones sin comprometer el rendimiento. Los actores involucrados son el Agente de IA, el Sistema de Transacciones y el Monitor de Recursos. El objetivo es reducir el consumo de CPU y memoria en al menos un 20% sin afectar la latencia de las transacciones, que debe mantenerse por debajo de 100ms.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | Optimización de componentes IA |
| **Nivel** | advanced-l2 |
| **Tipo** | practical |
| **Tiempo estimado** | 8 horas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Análisis de consumo de recursos

**Objetivo:** Identificar las áreas de mayor consumo de recursos en el agente de IA.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- Evalúa el consumo de CPU y memoria del agente durante la ejecución de transacciones típicas.
- Identifica los componentes o funciones que consumen más recursos.

**Entregable:** Informe de análisis de consumo de recursos que incluya gráficas y tablas comparativas.

<details>
<summary>Pistas de conocimiento</summary>

- Métodos de profiling y monitoreo de aplicaciones Java.

</details>

### Fase 2: Implementación de optimizaciones

**Objetivo:** Aplicar técnicas de optimización para reducir el consumo de recursos.

**Tiempo estimado:** 4 horas

**Instrucciones:**

- Aplica técnicas de optimización de código y algoritmos para reducir el consumo de CPU y memoria.
- Verifica que las optimizaciones no afecten la latencia de las transacciones.

**Entregable:** Código optimizado del agente de IA con comentarios explicando las modificaciones realizadas.

<details>
<summary>Pistas de conocimiento</summary>

- Técnicas de optimización de algoritmos y estructuras de datos en Java.

</details>

### Fase 3: Evaluación y ajustes finales

**Objetivo:** Evaluar el impacto de las optimizaciones y realizar ajustes finales.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- Realiza pruebas de carga para evaluar el impacto de las optimizaciones en el consumo de recursos y la latencia de las transacciones.
- Realiza ajustes finales basados en los resultados de las pruebas.

**Entregable:** Reporte de evaluación que incluya resultados de pruebas de carga y ajustes realizados.

<details>
<summary>Pistas de conocimiento</summary>

- Herramientas y técnicas para realizar pruebas de carga en aplicaciones Java.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué son las técnicas de optimización de recursos en el contexto de agentes de IA?
- **paraQueSirve**: ¿Para qué sirven las técnicas de optimización de recursos en el contexto del agente de IA de PragmaBank?
- **comoSeUsa**: ¿Cómo se aplican las técnicas de optimización de recursos en el código del agente de IA?
- **erroresComunes**: ¿Cuáles son los errores comunes al aplicar técnicas de optimización de recursos?
- **queDecisionesImplica**: ¿Qué decisiones debes tomar al evaluar el impacto de las optimizaciones y realizar ajustes finales?

## Criterios de Evaluacion

- Identificación correcta de áreas de mayor consumo de recursos.
- Aplicación efectiva de técnicas de optimización de código y algoritmos.
- Verificación de que las optimizaciones no afectan la latencia de las transacciones.
- Realización de pruebas de carga y ajustes finales basados en resultados.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
mvn clean compile
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
