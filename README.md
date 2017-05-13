# batch-rename
Batch Rename Files Java GUI

/*
 * AUTHOR     : Kanta Kikubo
 * AUTHOR URL : http://www.trance-cat.com/
 * LICENSE    : Apache 2.0
 */
/*
 /-------------------BatchRename V3.0-------------------/

 重要な更新履歴
 2015/3/2 NullPointerExceptionはhowmanyとhowmanyfilesの「該当ファイル表示」前のリセットで解消。
 2015/3/2 wordlist.txtのデフォルト場所をローカルに指定。パス指定の引数は削除していない。
 2015/5/30 wordlist.txtをwordlist.iniに変更
 2015/6/30 サブディレクトリチェックボックスを考慮するよう変更。ホームページをtrance-catに変更。
 2015/7/31 新処理方法。マルチスレッド化。XMLによる設定保存機能追加。
 2016/2/20 レイアウトをFXMLに変更。文字列登録機能追加。
 2016/2/21 アイコン追加。

 Ver2.0での課題
 ListViewでwordlistの内容を編集できるようにする。必要ならばwordlistをxml形式に。

 ルール設定(例：_を-に置き換えるなどのルールを設定できるようにする)
 
 */