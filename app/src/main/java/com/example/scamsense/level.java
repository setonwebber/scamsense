    package com.example.scamsense;

    public class level {
        private int questions;
        private int rightAnswers;

        public level() {
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

        public level(int questions) {
            this.questions = questions;
            this.rightAnswers = 0;
        }
    }
