describe('App (navigator.app)', function () {
    it("app.spec.1 should exist app", function () {
        expect(navigator.app).toBeDefined();
    });

    describe("openUrl", function() {
        it("app.spec.2 should exist openUrl", function() {
            expect(typeof navigator.app.openUrl).toBeDefined();
            expect(typeof navigator.app.openUrl == 'function').toBe(true);
        });
    });
    if (isAndroid() || isWindowsPhone()) {
        describe("exitApp", function() {
                it("app.spec.3 should exist exitApp", function() {
                    expect(typeof navigator.app.exitApp).toBeDefined();
                    expect(typeof navigator.app.exitApp == 'function').toBe(true);
                });
        });

        describe("backHistory", function() {
                it("app.spec.4 should exist backHistory", function() {
                    expect(typeof navigator.app.backHistory).toBeDefined();
                    expect(typeof navigator.app.backHistory == 'function').toBe(true);
                });
        });
    }
    if (isAndroid()) {
        describe("install", function() {
                it("app.spec.5 should exist install", function() {
                    expect(typeof navigator.app.install).toBeDefined();
                    expect(typeof navigator.app.install == 'function').toBe(true);
                });
        });

        describe("clearHistory", function() {
                it("app.spec.6 should exist clearHistory", function() {
                    expect(typeof navigator.app.clearHistory).toBeDefined();
                    expect(typeof navigator.app.clearHistory == 'function').toBe(true);
                });
        });

        describe("clearCache", function() {
                it("app.spec.7 should exist clearCache", function() {
                    expect(typeof navigator.app.clearCache).toBeDefined();
                    expect(typeof navigator.app.clearCache == 'function').toBe(true);
                });
        });
    }

});
