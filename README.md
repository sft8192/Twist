# Twist

twitterのツイートをstormでワードカウントしてつぶやきビッグデータみたいなものを表示する
![つぶやきビッグデータ](https://github.com/sft8192/images/blob/master/twist.png)

## Description

1. 全公開ツイートの1%を抽出し、Stormに流す
2. Stormでワードカウントして、JSONで出力する
3. D3jsでつぶやきビッグデータみたいなのを表示する

* Eclipseを使ってこれをスタンドアロンでサクっと動作させることが可能です
* 分散環境でも動きます、Apache Ambariを使用して6台で動作確認済

## Requirement

twitter開発者アカウント

srcフォルダ直下に以下の内容のtwitter4j.propertiesを作成

    debug=true
    oauth.consumerKey=****************
    oauth.consumerSecret=****************
    oauth.accessToken=****************
    oauth.accessTokenSecret=****************

nginx

## CreateAccount

1. Twitterアカウントを作成
2. 携帯電話番号を登録して認証
3. https://apps.twitter.com/ にアクセスしてCreate a new applicationをクリック
4. [Name]、[Description]、[Website]を適当に入力
5. [Key and Access Tokens]タブの[Consumer key] と [Consumer secret] をメモ
6. [Create my access token] をクリックし[Access token] と [Access token secret] をメモ


## Usage

1. nginxインストール、起動  
2. tools/Rankings.java 161行目のjsonファイル出力先をnginxの公開ディレクトリに指定  
3. 公開ディレクトリにindex.htmlを置く  
4. ブラウザでlocalhost:8080を開く  

## UserDictionary

* resourses/dic.csvに除外したい単語(BOTが頻繁に使う単語とか)を入れておくとカウント対象外になる
* resourses/user_dic.csvはwikipediaの見出しから作った辞書、使用すると負荷がかかるため注意（未使用）
* SplitBolt.javaあたりを見てもらうと読み込むユーザ辞書の設定が可能

