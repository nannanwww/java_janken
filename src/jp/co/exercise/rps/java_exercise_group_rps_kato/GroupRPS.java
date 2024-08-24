package java_exercise_group_rps_kato;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class GroupRPS {
	// 出力色を設定
	public static final String red     = "\u001b[31m";
	public static final String green   = "\u001b[32m";
	public static final String yellow  = "\u001b[33m";
	public static final String blue    = "\u001b[34m";
	public static final String reset   = "\u001b[0m";

    // 定数を設定（1: グー, 2: チョキ, 3: パー）
    public static final int ROCK = 1;
    public static final int SCISSORS = 2;
    public static final int PAPER = 3;
    
    // 数値をグー、チョキ、パーに変換するメソッド
    public static String getChoiceName(int choice) {
        switch (choice) {
            case ROCK:
                return "グー";
            case SCISSORS:
                return "チョキ";
            case PAPER:
                return "パー";
            default:
                return "無効";
        }
    }

    // 勝敗情報を格納
    static boolean lastLoserIsPlayer = false;
    static boolean lastLoserIsPC = false;
    static boolean lastIsDraw = false;


    public static void main(String[] args) throws IOException {
        Random random = new Random();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // パーティー編成
        // プレイヤー
        String[] team1 = {
            "先鋒",
            "次鋒",
            "中堅",
            "副将",
            "大将"
        };

        // PC
        String[] team2 = {
            "先鋒",
            "次鋒",
            "中堅",
            "副将",
            "大将"
        };

        // 初期選手を設定
        int playerParty = 0;
        int pcParty = 0;

        // ターン開始
        while (playerParty < 5 && pcParty < 5) {
            // パーティーの宣言
            if (playerParty == 0 && pcParty == 0 && !lastIsDraw) {
                System.out.println("【じゃんけん開始】");
                System.out.println("「プレイヤーチームとPCチームの先鋒は前へ」");
            } else if (!lastIsDraw && lastLoserIsPlayer) {
                // 前のターンでプレイヤーが負けた場合
                System.out.println("「プレイヤーチームの" + team1[playerParty] + "は前へ」");
            } else if (!lastIsDraw && lastLoserIsPC) {
                // 前のターンでPCが負けた場合
                System.out.println("「PCチームの" + team2[pcParty] + "は前へ」");
            }

            // ユーザーの入力を取得
            int playerChoice = 0;
            while (true) {
                System.out.println("「じゃんけんの手を選択してください」");
                System.out.println("1: グー");
                System.out.println("2: チョキ");
                System.out.println("3: パー");
                System.out.print(">");
                String input = reader.readLine();

                // 入力情報を数値へ変換
                try {
                    playerChoice = Integer.parseInt(input);

                    // 整数1～3以外が入力された場合に再入力させる
                    if (playerChoice < 1 || playerChoice > 3) {
                        System.out.println(red + "無効な入力です。数字の 1, 2, 3 を入力してください。" + reset);
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(red + "無効な入力です。数字の 1, 2, 3 を入力してください。" + reset);
                }
            }

            // PCの選択を生成
            int pcChoice = random.nextInt(3) + 1;

            // プレイヤーとPCのじゃんけん選択を表示
            System.out.println("【" + team1[playerParty] + " vs " + team2[pcParty] + "戦】");
            System.out.println(getChoiceName(playerChoice) + " vs " + getChoiceName(pcChoice));

            // 勝敗判定処理
            if (playerChoice == pcChoice) {
            	// 引き分け
                System.out.println(green + "引き分けです!" + reset);
                lastIsDraw = true;
            } else if ((playerChoice == ROCK && pcChoice == SCISSORS) ||
                       (playerChoice == SCISSORS && pcChoice == PAPER) ||
                       (playerChoice == PAPER && pcChoice == ROCK)) {
                // プレイヤーが勝った場合
                System.out.println(blue + "プレイヤーが勝ちました!" + reset);
                pcParty = updateGameState(playerParty, pcParty, false);// 敗者処理
            } else {
                // プレイヤーが負けた場合
                System.out.println(red + "PCが勝ちました!" + reset);
                playerParty = updateGameState(playerParty, pcParty, true);// 敗者処理
            }

            // どちらかのチームが全員負けた場合
            if (playerParty >= 5) {
            	System.out.println(red + "【じゃんけん終了】");
                System.out.println("PCチームの勝ちです！" + reset);
                break;
            } else if (pcParty >= 5) {
            	System.out.println(blue + "【じゃんけん終了】");
                System.out.println("プレイヤーチームの勝ちです！" + reset);
                break;
            }

            // ターン終了時に改行
            System.out.println("\n");
        }
    }

    // 勝敗情報更新&選手交代
    public static int updateGameState(int playerParty, int pcParty, boolean playerLost) {
        if (playerLost) {
            lastLoserIsPlayer = true;
            lastLoserIsPC = false;
            return playerParty + 1;
        } else {
            lastLoserIsPlayer = false;
            lastLoserIsPC = true;
            return pcParty + 1;
        }
    }
}