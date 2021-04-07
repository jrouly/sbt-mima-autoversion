package net.rouly.sbt.autoversion.models

import net.rouly.sbt.autoversion.model.bumpOrdering
import org.scalacheck.Gen
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import sbtrelease.Version.Bump

class BumpImplicitsSpec extends AnyFlatSpec with Matchers with ScalaCheckPropertyChecks {
  override implicit val generatorDrivenConfig: PropertyCheckConfiguration =
    PropertyCheckConfiguration(minSuccessful = 30)

  private val bumpGen: Gen[Bump] = Gen.oneOf(Bump.Major, Bump.Minor, Bump.Bugfix)
  private val bumpListGen = Gen.nonEmptyListOf(bumpGen)

  it should "always prioritize a major bump" in {
    forAll(bumpListGen) { bumps =>
      if (bumps.contains(Bump.Major)) {
        bumps.max shouldBe Bump.Major
      }
    }
  }

  it should "prioritize a minor bump if there is no major bump" in {
    forAll(bumpListGen) { bumps =>
      if (!bumps.contains(Bump.Major) && bumps.contains(Bump.Minor)) {
        bumps.max shouldBe Bump.Minor
      }
    }
  }

  it should "prioritize a bugfix bump if there is no other kinds of bump" in {
    forAll(bumpListGen) { bumps =>
      if (bumps.forall(_ == Bump.Bugfix)) {
        bumps.max shouldBe Bump.Bugfix
      }
    }
  }
}
