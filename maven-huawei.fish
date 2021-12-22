mkdir ~/.sbt -p

cat > ~/.sbt/repositories << EOF
[repositories]
  local
  huaweicloud-ivy: https://mirrors.huaweicloud.com/repository/ivy/, [organization]/[module]/(scala_[scalaVersion]/)(sbt_[sbtVersion]/)[revision]/[type]s/[artifact](-[classifier]).[ext]
  huaweicloud-maven: https://mirrors.huaweicloud.com/repository/maven/
  bintray-typesafe-ivy: https://dl.bintray.com/typesafe/ivy-releases/, [organization]/[module]/(scala_[scalaVersion]/)(sbt_[sbtVersion]/)[revision]/[type]s/[artifact](-[classifier]).[ext]
  bintray-sbt-plugins: https://dl.bintray.com/sbt/sbt-plugin-releases/, [organization]/[module]/(scala_[scalaVersion]/)(sbt_[sbtVersion]/)[revision]/[type]s/[artifact](-[classifier]).[ext], bootOnly

EOF
