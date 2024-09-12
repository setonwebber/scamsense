    package com.example.scamsense;

    public class Level {
        private int questions;
        private int rightAnswers;
        private int highScore;

        public Level() {
            this.questions = 0;
            this.rightAnswers = 0;
        }

        public int getRightAnswers() {
            return rightAnswers;
        }

        public int getQuestions() {
            return questions;
        }

        public void setRightAnswers(int rightAnswers) {
            this.rightAnswers = rightAnswers;
        }

        public Level(int questions) {
            this.questions = questions;
            this.rightAnswers = 0;
        }
    }
