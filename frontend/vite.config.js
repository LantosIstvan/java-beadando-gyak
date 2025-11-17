import { defineConfig } from 'vite';
import { resolve } from 'path';
import { version as appVersion } from './package.json';

const nodeVersion = process.version;
const npmUserAgent = process.env.npm_config_user_agent;

let npmVersionInfo = 'N/A (run without npm)';
if (npmUserAgent) {
    npmVersionInfo = npmUserAgent.split(' ').find(part => part.startsWith('npm/'));
}

console.log(
`------------------------------------
🚀 Vite build started...
📦 App version:  ${appVersion}
💡 Node version: ${nodeVersion}
🤖 NPM version:  ${npmVersionInfo}
------------------------------------`
);

export default defineConfig({
  build: {
    outDir: resolve(import.meta.dirname, '../src/main/resources/static/assets'),
    emptyOutDir: true,
    manifest: false,
    rollupOptions: {
      input: {
        'style': resolve(import.meta.dirname, 'css/style.css'),
        'main': resolve(import.meta.dirname, 'js/main.js'),
      },
      output: {
        entryFileNames: `[name]-${appVersion}.js`,
        chunkFileNames: `[name]-${appVersion}.js`,
        assetFileNames: `[name]-${appVersion}.[ext]`,
      },
    },
  },
});
