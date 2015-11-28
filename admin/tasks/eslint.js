'use strict';

module.exports = function eslint(grunt) {
    grunt.loadNpmTasks('grunt-eslint');
    return {
        options: {
            configFile: '.eslintrc',
            rulePaths: ['node_modules/eslint/lib/rules']
        },
        target: ['index.js',
            'controllers/**/*.js',
            'lib/**/*.js',
            'routes/**/*.js',
            'models/**/*.js'
        ]
    };
};
