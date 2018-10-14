
<template>
    <div>
        <form name="registration" method="post" action="/account/register" id="form-registration" class="validate" novalidate="novalidate">

            <div class="col-12">

                <div class="col-12">
                    <div class="input-group input-group-lg mb-3">
                        <label for="registration_firstname" class="required">Vorname</label>
                        <input type="text" id="registration_firstname" name="registration[firstname]" required="required" placeholder="Vorname">

                    </div>
                </div>

                <div class="col-12">
                    <div class="input-group input-group-lg mb-3">
                        <label for="registration_lastname" class="required">Nachname</label>
                        <input type="text" id="registration_lastname" name="registration[lastname]" required="required" placeholder="Nachname">

                    </div>
                </div>

                <div class="col-12">
                    <div class="input-group input-group-lg mb-3">
                        <label for="registration_email" class="required">E-Mail</label>
                        <input type="email" id="registration_email" name="registration[email]" required="required" placeholder="E-Mail">

                    </div>
                </div>

                <div class="col-12">
                    <div class="input-group input-group-lg mb-3">
                        <label for="registration_password_first" class="required">Passwort</label>
                        <input type="password" id="registration_password_first" name="registration[password][first]" required="required" placeholder="Passwort">

                    </div>
                </div>

            </div>

            <div class="col-12">
                <div class="col-12 pl-3 pr-3">
                    <div class="button-wrapper">
                        <div class="row">
                            <div class="col-12">
                                <div class="input-group input-group-lg">

                                    <input name="submit" type="hidden" value="true">
                                    <button data-action="/de/account/register" data-state="account" class="btn btn-primary btn-lg btn-block register-btn">ANMELDEN</button>
                                </div>

                            </div>
                        </div>

                    </div>

                    <div class="button-wrapper mt-4">
                        <div class="row">
                            <div class="col-12">
                                <div class="input-group input-group-lg">
                                    <button data-action="/de/account/login" data-state="account" class="btn btn-outline-secondary btn-lg btn-block edit-btn">
                                        LOGIN
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <input type="hidden" id="registration__token" name="registration[_token]" value="">
        </form>
    </div>
</template>

<script>
    export default {
        data : () => {
            return {
                age : 56
            }
        },
        methods : {
            incAge() {
                this.age+=1;
            }
        }
    }
</script>

<style lang="scss" scoped>
    h1 {
        color: red;
    }
</style>