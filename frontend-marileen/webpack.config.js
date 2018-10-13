const { VueLoaderPlugin } = require('vue-loader');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const webpack = require('webpack');

module.exports = {

    //entry : './js-webpack/script1.js',

    // output : {
    //     path : __dirname + '/dist',
    //     filename: 'bundle.js'
    // },

    entry : './src/app.js',

    devServer: {
        hot: true,
        watchOptions: {
            poll: true
        }
    },

    module: {
        rules: [
            {
                test: /\.scss$/,
                use: [
                    "vue-style-loader", // creates style nodes from JS strings
                    "css-loader", // translates CSS into CommonJS
                    "sass-loader" // compiles Sass to CSS, using Node Sass by default
                ]
            },
            {
                test: /\.vue$/,
                use: 'vue-loader'
            }
        ] //rules
    }, // module

    plugins: [
        new webpack.HotModuleReplacementPlugin(),
        new VueLoaderPlugin(),
        new HtmlWebpackPlugin({
            filename: 'index.html',
            template: 'index.html',
            inject: true
        })
    ]
};