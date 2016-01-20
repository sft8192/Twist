# Twist

TWItterのツイートをSTormでワードカウントするプログラム

## Description

1. 全公開ツイートの1%を抽出し、Stormに流す
2. Stormでワードカウントして、JSONで出力する
3. D3jsでつぶやきビッグデータみたいなのを表示する

## Requirement

twitter開発者アカウント

## CreateAccount

1. Twitterアカウントを作成
2. 携帯電話番号を登録して認証
3. https://apps.twitter.com/ にアクセスしてCreate a new applicationをクリック
4. [Name]、[Description]、[Website]を適当に入力
5. [Key and Access Tokens]タブの[Consumer key] と [Consumer secret] をメモ
6. [Create my access token] をクリックし[Access token] と [Access token secret] をメモ


## Usage

srcフォルダ直下に以下の内容のtwitter4j.propertiesを作成

    debug=true
    oauth.consumerKey=****************
    oauth.consumerSecret=****************
    oauth.accessToken=****************
    oauth.accessTokenSecret=****************


nginxインストール、起動
tools/Rankings.java 161行目のjsonファイル出力先をnginxの公開ディレクトリに指定
公開ディレクトリにhtmlファイル
ブラウザでlocalhost:8080



