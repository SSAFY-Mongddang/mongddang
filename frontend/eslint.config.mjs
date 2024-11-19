// @ts-check

import eslint from '@eslint/js';
import tseslint from 'typescript-eslint';
import prettierConfig from 'eslint-config-prettier';

export default tseslint.config(
  eslint.configs.recommended,
  /**
   * https://typescript-eslint.io/users/configs/#strict
   * This configuration is not considered "stable" under Semantic Versioning (semver). 
   * Its enabled rules and/or their options may change outside of major version updates.
   */
  // ...tseslint.configs.strict,
  // ...tseslint.configs.stylistic,
  // ...tseslint.configs.recommendedTypeChecked,
  ...tseslint.configs.strictTypeChecked,
  ...tseslint.configs.stylisticTypeChecked,
  prettierConfig,
  // https://typescript-eslint.io/getting-started/typed-linting
  {
    languageOptions: {
      parserOptions: {
        // indicates to ask TypeScript's type checking service 
        // for each source file's type information (see Parser#projectService).
        projectService: true,
        // tells our parser the absolute path of your project's root directory 
        // (see Parser#tsconfigRootDir).
        tsconfigRootDir: import.meta.dirname,
      },
    },
  },
);