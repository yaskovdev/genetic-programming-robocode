ROBOCODE_HOME=/Users/yaskovdev/robocode
mvn clean compile assembly:single
rm -rf $ROBOCODE_HOME/robots/*.jar
rm -rf $ROBOCODE_HOME/robots/robot.database
cp target/robocode-push-*.jar $ROBOCODE_HOME/robots
