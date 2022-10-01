package week2.controller;

import week2.utility.Convertor;

public class TrainingMain {

    public static void main(String[] args) {
        BasketBallApiController.basketBallApi(false);
        System.out.println(Convertor.strArr.size());
        String sub = "BasketBall API Data Reports";
        String msg = "Hi Everyone,\n\n" +
                "Kindly find enclosed the generated reports for BasketBall API.\n\n" + "Note: This is a system generated mail. Do not reply. \n\n" + "Regards,\nAparajita";
        Convertor.MailSender.sendEmail(Convertor.strArr, "", msg, sub);
        //BasketBallChartController.basketBallChart();
    }
}
