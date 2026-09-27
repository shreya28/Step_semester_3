public class Problem1_OnlineExaminationSystem {

    static abstract class Question {

        protected String questionText;
        protected String correctAnswer;

        Question(String questionText, String correctAnswer) {
            this.questionText = questionText;
            this.correctAnswer = correctAnswer;
        }

        abstract boolean checkAnswer(String answer);
    }

    static class MultipleChoiceQuestion extends Question {

        MultipleChoiceQuestion(
                String questionText,
                String correctAnswer) {

            super(questionText, correctAnswer);
        }

        @Override
        boolean checkAnswer(String answer) {
            return correctAnswer.equalsIgnoreCase(answer);
        }
    }

    static class TrueFalseQuestion extends Question {

        TrueFalseQuestion(
                String questionText,
                String correctAnswer) {

            super(questionText, correctAnswer);
        }

        @Override
        boolean checkAnswer(String answer) {
            return correctAnswer.equalsIgnoreCase(answer);
        }
    }

    static class Student {

        String name;

        Student(String name) {
            this.name = name;
        }
    }

    static class Examination {

        String title;
        Question[] questions;

        Examination(String title, Question[] questions) {
            this.title = title;
            this.questions = questions;
        }
    }

    static class Attempt {

        Student student;
        Examination examination;
        String[] answers;
        boolean submitted;

        Attempt(Student student, Examination examination) {

            this.student = student;
            this.examination = examination;
            this.answers =
                    new String[examination.questions.length];
        }

        void answerQuestion(int number, String answer) {

            if (submitted) {
                System.out.println(
                        "Cannot change answer after submission.");
                return;
            }

            answers[number - 1] = answer;

            System.out.println(
                    "Question " + number
                    + " answered with '" + answer + "'.");
        }

        void submit() {

            if (submitted) {
                System.out.println(
                        "Attempt already submitted.");
                return;
            }

            submitted = true;

            System.out.println(
                    "Examination '" + examination.title
                    + "' submitted successfully.");

            evaluate();
        }

        void evaluate() {

            int correct = 0;

            for (int i = 0;
                 i < examination.questions.length;
                 i++) {

                if (answers[i] != null
                        && examination.questions[i]
                        .checkAnswer(answers[i])) {

                    correct++;
                }
            }

            System.out.println(
                    "Result for '" + examination.title
                    + "' attempt: "
                    + correct + "/"
                    + examination.questions.length
                    + " correct");
        }
    }

    public static void main(String[] args) {

        Student student =
                new Student("Shreya");

        Question[] questions = {

            new MultipleChoiceQuestion(
                    "Capital of France?",
                    "A"),

            new MultipleChoiceQuestion(
                    "2 + 2 = ?",
                    "B")
        };

        Examination exam =
                new Examination(
                        "Math Quiz",
                        questions);

        Attempt attempt =
                new Attempt(student, exam);

        System.out.println(
                "Examination 'Math Quiz' started by Student.");

        attempt.answerQuestion(1, "A");
        attempt.answerQuestion(2, "C");

        attempt.submit();

        attempt.answerQuestion(1, "B");
    }
}