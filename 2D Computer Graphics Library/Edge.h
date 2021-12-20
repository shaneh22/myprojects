class Edge {
    public:
        Edge(float iSlope, int iTop, int iBottom, float iCurrX, int iWind){
            slope = iSlope;
            top = iTop;
            bottom = iBottom;
            currX = iCurrX;
            wind = iWind;
        }

        float getSlope() const { return slope; }
        int getTop() const { return top; }
        int getBottom() const { return bottom; }
        float getCurrX() const { return currX; }
        int getWind() const { return wind; }

        void setSlope(float newSlope) {slope = newSlope; }
        void setTop(float newTop) {top = newTop; }
        void setBottom(float newBottom) {bottom = newBottom;}
        void setCurrX(float newX) { currX = newX; }
        void addToCurrX(float add) { currX += add; }

    private:
        float slope;
        int top;
        int bottom;
        float currX;
        int wind;
};